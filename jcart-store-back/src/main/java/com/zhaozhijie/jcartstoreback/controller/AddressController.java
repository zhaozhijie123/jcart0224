package com.zhaozhijie.jcartstoreback.controller;

import com.zhaozhijie.jcartstoreback.dto.in.AddressCreateInDTO;
import com.zhaozhijie.jcartstoreback.dto.in.AddressUpdateInDTO;
import com.zhaozhijie.jcartstoreback.dto.out.AddressListOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.AddressShowOutDTO;
import com.zhaozhijie.jcartstoreback.po.Address;
import com.zhaozhijie.jcartstoreback.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/address")
@CrossOrigin
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/getCustomerAddress")
    public List<AddressListOutDTO> getAddressByCustomerId(@RequestAttribute Integer customerId){
        List<Address> addresses = addressService.getByCustomerId(customerId);

        List<AddressListOutDTO> addressListOutDTOS = addresses.stream().map(address -> {
            AddressListOutDTO addressListOutDTO = new AddressListOutDTO();
            addressListOutDTO.setAddressId(address.getAddressId());
            addressListOutDTO.setContent(address.getContent());
            addressListOutDTO.setReceiverName(address.getReceiverName());
            addressListOutDTO.setReceiverMobile(address.getReceiverMobile());
            addressListOutDTO.setContent(address.getContent());
            return addressListOutDTO;
        }).collect(Collectors.toList());

        return addressListOutDTOS;
    }

    @GetMapping("/getById")
    public AddressShowOutDTO getById(@RequestParam Integer addressId){
        Address address = addressService.getById(addressId);
        AddressShowOutDTO addressShowOutDTO = new AddressShowOutDTO();
        addressShowOutDTO.setAddressId(address.getAddressId());
        addressShowOutDTO.setTag(address.getTag());
        addressShowOutDTO.setReceiverName(address.getReceiverName());
        addressShowOutDTO.setReceiverMobile(address.getReceiverMobile());
        addressShowOutDTO.setContent(address.getContent());

        return addressShowOutDTO;
    }

    @PostMapping("/create")
    public Integer create(@RequestBody AddressCreateInDTO addressCreateInDTO,
                          @RequestAttribute Integer customerId){
        System.out.println(addressCreateInDTO.getReceiverName()+"******************");
        Address address = new Address();
        address.setCustomerId(customerId);
        address.setTag(addressCreateInDTO.getTag());
        address.setReceiverName(addressCreateInDTO.getReceiverName());
        address.setReceiverMobile(addressCreateInDTO.getReceiverMobile());
        address.setContent(addressCreateInDTO.getContent());

        addressService.create(address);
        Integer addressId = address.getAddressId();
        return addressId;
    }

    @PostMapping("/update")
    public void update(@RequestBody AddressUpdateInDTO addressUpdateInDTO,
                       @RequestAttribute Integer customerId){
        Address address = new Address();
        address.setAddressId(addressUpdateInDTO.getAddressId());
        address.setTag(addressUpdateInDTO.getTag());
        address.setReceiverName(addressUpdateInDTO.getReceiverName());
        address.setReceiverMobile(addressUpdateInDTO.getReceiverMobile());
        address.setContent(addressUpdateInDTO.getContent());
        addressService.update(address);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody Integer addressId){
        addressService.delete(addressId);
    }
}
