import CustomFormItem from '../../../common/custom-form-item';
import SelectCompanyTree from '../../../common/select-companyTree.vue';
import util from '../../../../libs/util';

export default {
    name: 'base-info-form',
    components: {
        CustomFormItem,
        SelectCompanyTree
    },
    props: ['initData', 'option', 'laywerDisplay'],
    data () {
        return {
            spvStatus: null,
            baseInfo: {},
            companyModelShow: false,
            guaranteeTypeOptions: [],
            validateRules: {
                spvName: [
                    {required: true, message: '请选择所属SPV', trigger: 'change'}
                ],
                intentAmount: [
                    {required: true, message: '请输入意向金额', trigger: 'blur'},
                    {pattern:/^[-+]?\d{1,14}(\.\d{1,2})?$/, message: '请输入最大14位整数及2位小数的数字', trigger: 'blur'}
                ],
                paymentRate: [
                    {required: false, message: '请输入支付比率', trigger: 'blur'}
                ],
                guaranteeTypeData: [
                    {'message': '请选择担保类型', 'required': 1},
                    {'pattern': null, 'type': 'array', 'message': null}
                ],
                applyDate: [
                    {required: true, type: 'date', message: '请输入申请日期', trigger: 'change'}
                ],
            }
        };
    },
    computed: {
        // 债券品种
        bondCategoryOptions () {
            return this.getDict('bondCategory');
        },
        // 债券发行地
        bondIssuePlaceOptions () {
            return this.getDict('cisborder');
        },
        // 发行场所
        issuePlaceOptions () {
            return this.getDict('issuePlace');
        },
        // 币种
        currencyOptions () {
            return this.getDict('currency');
        },
        // // 担保类型
        // guaranteeTypeOptions () {
        //     return this.getDict('guaranteeModeForQuota');
        // },
        // 承销费支付方式
        paymentTypeOptions () {
            return this.getDict('bondPaymentType');
        }
    },
    watch: {
        initData: {
            handler (val) {
                this.baseInfo = val;
            },
            deep: true
        },
        'baseInfo.applyDate'(val) {
            console.info(val)
        }
    },
    created () {
        this.baseInfo = this.initData;
        this.baseInfo.spvName = '';
        this.baseInfo.bondCategory = '02';
        this.baseInfo.bondIssuePlace = '1';
        this.baseInfo.issuePlace = '1';
        this.baseInfo.currency = 'CNY';
        this.baseInfo.paymentType = '1';
        let user = util.getLoginUser();
        let date = util.formatDate(new Date(), 'yyyy-MM-dd');
        this.baseInfo.applyUser = user.staffName;
        this.baseInfo.applyDate = date;
        console.info(this.baseInfo.applyDate)
        // 担保类型从主数据获取
        util.ajax.get('/fund/guaranteeMode/listall').then(res => {
            let data = res.data;
            for (var i = 0; i < data.length; i++) {
                this.guaranteeTypeOptions.push({
                    dictCode: data[i].guaranteeNumber,
                    dictValueZhCn: data[i].guaranteeName
                });
            }
            // // 担保类型默认值：信用
            // this.baseInfo.guaranteeTypeData.push('G20181226000029');
        }).catch(() => {
        });
    },
    methods: {
        // 打开选择申请单位组件
        onCompanySelect () {
            if (this.option.disabled) {
                return;
            }
            this.companyModelShow = true;
        },
        // 选择申请单位组件的回调(对话框显示状态)
        selectCompanyStatus (showStatus) {
            this.companyModelShow = showStatus;
        },
        // 选择申请单位组件的回调(数据)
        selectCompanyCallback (data) {
            const company = JSON.parse(data);
            this.baseInfo.spvName = company.selectCompanyName[0];
            this.baseInfo.applySpv = company.selectCompanyCode[0];
        },
        // 验证是否通过
        isValidate () {
            let isValidate = false;
            console.info(this.baseInfo.applyDate)
            this.baseInfo.paymentRate = this.baseInfo.paymentRate+ '';
            this.baseInfo.intentAmount = this.baseInfo.intentAmount+ '';
            this.$refs['baseForm'].validate((valid) => { // 根据 ref 检查校验
                isValidate = valid;
            });
            return isValidate;
        },
        // 担保方式监听事件
        selectGuaranteeMode (data) {
            this.$emit('baseInfoBack', data);
        },
        // 承销费支付方式监听事件
        selectPaymentType (data) {
            // 承销费支付方式 == 按比例支付，支付比率不为空
            if (data == 3) {
                this.validateRules.paymentRate[0].required = true;
            } else {
                this.validateRules.paymentRate[0].required = false;
            }
        }
    }
};
