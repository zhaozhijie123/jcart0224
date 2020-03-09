package com.zhaozhijie.jcartadministrationback.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhaozhijie.jcartadministrationback.constant.ClientExceptionConstant;
import com.zhaozhijie.jcartadministrationback.dao.AdministratorMapper;
import com.zhaozhijie.jcartadministrationback.dto.in.AdministratorResetPwdInDTO;
import com.zhaozhijie.jcartadministrationback.exception.ClientException;
import com.zhaozhijie.jcartadministrationback.po.Administrator;
import com.zhaozhijie.jcartadministrationback.service.AdministratorService;
import com.zhaozhijie.jcartadministrationback.util.MailBean;
import com.zhaozhijie.jcartadministrationback.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorMapper administratorMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private SecureRandom secureRandom;

    @Override
    public Administrator getById(Integer administratorId) {
        Administrator administrator = administratorMapper.selectByPrimaryKey(administratorId);
        return administrator;
    }

    @Override
    public Administrator getByUsername(String username) {
        Administrator administrator = administratorMapper.selectByUsername(username);
        return administrator;
    }

    @Override
    public Integer create(Administrator administrator) {
        administratorMapper.insertSelective(administrator);
        Integer administratorId = administrator.getAdministratorId();
        return administratorId;
    }

    @Override
    public void update(Administrator administrator) {
        administratorMapper.updateByPrimaryKeySelective(administrator);
    }

    @Override
    public void delete(Integer administratorId) {
        administratorMapper.deleteByPrimaryKey(administratorId);
    }

    @Override
    public void batchDelete(List<Integer> administratorIds) {
        administratorMapper.batchDelete(administratorIds);
    }

    @Override
    public Page<Administrator> getList(Integer pageNum) {
        PageHelper.startPage(pageNum, 10);
        Page<Administrator> page = administratorMapper.selectList();
        return page;
    }

    @Override
    public String getByEmail(String email) throws ClientException {
        Administrator administrator = administratorMapper.selectByEmail(email);
        if (administrator == null){
            throw new ClientException(ClientExceptionConstant.ADMINISTRATOR_EMAIL_NOT_EXIST_ERRCODE, ClientExceptionConstant.ADMINISTRATOR_EMAIL_NOT_EXIST_ERRMSG);
        }
        //生成随机重置码
        byte[] bytes = secureRandom.generateSeed(3);
        String hex = DatatypeConverter.printHexBinary(bytes);

        MailBean mailBean = new MailBean();
        mailBean.setReceiver(email);
        mailBean.setSubject("jcart管理端管理员密码重置");
        mailBean.setContent(hex);
        try {
            mailService.sendSimpleMail(mailBean);
            return hex;
        } catch (Exception e) {
            e.printStackTrace();
            return "发送失败";
        }
    }

    @Override
    public void restPwd(AdministratorResetPwdInDTO administratorResetPwdInDTO, Map<String, String> emailPwdResetCodeMap) throws ClientException {
        String email = administratorResetPwdInDTO.getEmail();
        if (email == null) {
            throw new ClientException(ClientExceptionConstant.ADMINISTRATOR_PWDRESET_EMAIL_NONE_ERRCODE, ClientExceptionConstant.ADMINISTRATOR_PWDRESET_EMAIL_NONE_ERRMSG);
        }

        //获取邮箱里面的重置码
        String innerResetCode = emailPwdResetCodeMap.get(email);
        if (innerResetCode == null) {
            throw new ClientException(ClientExceptionConstant.ADMINISTRATOR_PWDRESET_INNER_RESETCODE_NONE_ERRCODE, ClientExceptionConstant.ADMINISTRATOR_PWDRESET_INNER_RESETCODE_NONE_ERRMSG);
        }

        //获取页面重置码
        String outerResetCode = administratorResetPwdInDTO.getResetCode();
        if (outerResetCode == null) {
            throw new ClientException(ClientExceptionConstant.ADMINISTRATOR_PWDRESET_OUTER_RESETCODE_NONE_ERRCODE, ClientExceptionConstant.ADMINISTRATOR_PWDRESET_OUTER_RESETCODE_NONE_ERRMSG);
        }

        //比较重置码是否一致
        if (!outerResetCode.equalsIgnoreCase(innerResetCode)){
            throw new ClientException(ClientExceptionConstant.ADMINISTRATOR_PWDRESET_RESETCODE_INVALID_ERRCODE, ClientExceptionConstant.ADMINISTRATOR_PWDRESET_RESETCODE_INVALID_ERRMSG);
        }

        //获取当前管理员信息
        Administrator administrator = administratorMapper.selectByEmail(email);
        if (administrator == null){
            throw new ClientException(ClientExceptionConstant.ADMINISTRATOR_EMAIL_NOT_EXIST_ERRCODE, ClientExceptionConstant.ADMINISTRATOR_EMAIL_NOT_EXIST_ERRMSG);
        }

        //获取新的密码
        String newPwd = administratorResetPwdInDTO.getNewPwd();
        if (newPwd == null){
            throw new ClientException(ClientExceptionConstant.ADMINISTRATOR_NEWPWD_NOT_EXIST_ERRCODE, ClientExceptionConstant.ADMINISTRATOR_NEWPWD_NOT_EXIST_ERRMSG);
        }

        //重新加密 新密码
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, newPwd.toCharArray());
        administrator.setEncryptedPassword(bcryptHashString);

        //修改当前管理员
        administratorMapper.updateByPrimaryKeySelective(administrator);

        emailPwdResetCodeMap.remove(email);

    }
}
