import util from '../../../libs/util';
import ProcessTabs from '../../process/common/process-tabs';
import baseInfoForm from './components/base-info-form.vue';
import underwritorListTemplate from './components/underwritor-list-template.vue';
import agencyListTemplate from './components/agency-list-template.vue';
import pledgeListTemplate from './components/pledge-list-template.vue';
import guaranteeListTemplate from './components/guarantee-list-template.vue';
import mortgageListTemplate from './components/mortgage-list-template.vue';
import cashEpositListTemplate from './components/cashEposit-list-template.vue';
import stockListTemplate from './components/stock-list-template.vue';
import quotaInfo from './components/quota-detail.vue';
import guideList from '../../workflow/common/guide-list';

export default {
    name: 'quota-input-edit',
    components: {
        ProcessTabs,
        baseInfoForm,
        underwritorListTemplate,
        agencyListTemplate,
        pledgeListTemplate,
        guaranteeListTemplate,
        mortgageListTemplate,
        cashEpositListTemplate,
        stockListTemplate,
        quotaInfo,
        guideList
    },
    data () {
        return {
            // 基本信息
            baseInfo: {
                // 申请单位
                spvName: '',
                // 意向金额
                intentAmount: '',
                // 担保类型
                guaranteeTypeData: ['G20181226000029'],
            },
            // 意向承销商信息
            underwritorData: [],
            // 中介机构信息
            agencyData: [],
            // 担保信息
            guaranteeData: [],
            // 抵押信息
            mortgageData: [],
            // 质押信息
            pledgesData: [],
            // 存单质押
            cashEpositData: [],
            // 股权质押
            stockData: [],
            saveBtn: true,
            backBtn: true,
            guaranteeInfo: false,
            mortgageInfo: false,
            pledgeInfo: false,
            cashEpositInfo: false,
            stockInfo: false,
            option: {
                disabled: false, // 是否禁用
                button: true,    // 按钮是否可用
            },
            viewflag: false,     // 记录一下
            costEditFlag:false,
        };
    },
    methods: {

        // 初始化
        init () {
            let flag = this.$route.query.flag;
            let busId = this.$route.query.busId;
            if (flag == 'view') {
                this.saveBtn = false;
                this.option.disabled = true;
                this.option.button = false;
                this.viewflag = true;
            }
            if (flag == 'costEdit') {
                this.saveBtn = false;
                this.option.disabled = true;
                this.option.button = false;
                this.viewflag = true;
                this.costEditFlag=true;//费用维护
            }
            if (busId != undefined) {
                this.getDateilData(busId);
            }
        },
        // Tab事件监听
        onTabToggle (tabKey) {

        },
        // 获取额度录入详情信息
        getDateilData (busId) {
            this.$Spin.show(); // 加载loading
            util.ajax.get('/process/quotaApply/getDataById',
                {
                    params: {id: busId}
                }).then(res => {
                console.log('-------res', res);
                this.$Spin.hide();

                // 额度录入基本信息
                this.baseInfo = res.data.quotaApply;
                // 起始日期数据格式转换
                if (this.baseInfo.startDate != null) {
                    this.baseInfo.startDate = this.$formatDate(this.baseInfo.startDate);
                }
                // 结束日期数据格式转换
                if (this.baseInfo.endDate != null) {
                    this.baseInfo.endDate = this.$formatDate(this.baseInfo.endDate);
                }
                // 申请日期数据格式转换
                if (this.baseInfo.applyDate != null) {
                    this.baseInfo.applyDate = this.$formatDate(this.baseInfo.applyDate);
                }
                // 担保类型数据格式转换
                if (this.baseInfo.guaranteeType != null) {
                    this.baseInfo.guaranteeTypeData = this.baseInfo.guaranteeType.split(',');

                } else {
                    this.baseInfo.guaranteeTypeData = [];
                }

                // 意向承销商
                if (res.data.quotaUnderwritor != null) {
                    this.underwritorData = res.data.quotaUnderwritor;
                    this.$refs.underwritor.loadData(this.underwritorData);
                }

                // 中介机构
                if (res.data.quotaAgency != null) {
                    this.agencyData = res.data.quotaAgency;
                    this.$refs.agency.loadData(this.agencyData);
                }

                // 担保信息
                if (res.data.quotaGuarantees != null) {
                    this.guaranteeData = res.data.quotaGuarantees;
                    this.$refs.guarantee.loadData(this.guaranteeData);
                }

                // 抵押信息
                if (res.data.quotaMortgages != null) {
                    this.mortgageData = res.data.quotaMortgages;
                    this.$refs.mortgage.loadData(this.mortgageData);
                }

                // 质押信息
                if (res.data.quotaPledges != null) {
                    this.pledgesData = res.data.quotaPledges;
                    this.$refs.pledge.loadData(this.pledgesData);
                }

                // 存单质押
                if (res.data.quotaCertificate != null) {
                    this.cashEpositData = res.data.quotaCertificate;
                    this.$refs.cashEposit.loadData(this.cashEpositData);
                }
                // 股权质押
                if (res.data.quotaStocks != null) {
                    this.stockData = res.data.quotaStocks;
                    this.$refs.stock.loadData(this.stockData);
                }

            }).catch(() => {
                this.$Spin.hide();
            });
        },
        // 表单保存
        onSave () {

            // 基本信息表单及担保方式验证
            let valid = this.$refs.baseInfo.isValidate();
            // if ((this.guaranteeInfo && this.guaranteeData.length == 0) ||
            //     (this.pledgeInfo && this.pledgesData.length == 0) ||
            //     (this.mortgageInfo && this.mortgageData.length == 0) ||
            //     (this.cashEpositInfo && this.cashEpositData.length == 0) ||
            //     (this.stockInfo && this.stockData.length == 0)) {
            //     valid = false;
            // }

            if (valid) {
                this.$Spin.show(); // 加载loading

                util.ajax.post('/process/quotaApply/save', this.prepareFormData()).then(res => {
                    this.$Spin.hide();
                    this.baseInfo.id = res.data.id;
                    this.getDateilData(this.baseInfo.id);
                    this.$Modal.success({
                        title: '保存成功',
                        content: '您的额度录入已经保存成功。'
                    });
                }).catch(res => {
                    console.log(res);
                    this.$Message.error('保存失败！');
                    this.$Spin.hide();
                });
            } else {
                this.$Message.error('保存失败!请确认基本信息及担保信息是否填写正确。');
            }
        },
        // 表单提交
        onCommit () {

            // 基本信息表单及担保方式验证
            let valid = this.$refs.baseInfo.isValidate();
            // if ((this.guaranteeInfo && this.guaranteeData.length == 0) ||
            //     (this.pledgeInfo && this.pledgesData.length == 0) ||
            //     (this.mortgageInfo && this.mortgageData.length == 0) ||
            //     (this.cashEpositInfo && this.cashEpositData.length == 0) ||
            //     (this.stockInfo && this.stockData.length == 0)) {
            //     valid = false;
            // }

            if (valid) {
                this.$Spin.show(); // 加载loading

                util.ajax.post('/process/quotaApply/commit', this.prepareFormData()).then(res => {
                    this.$Spin.hide();
                    this.baseInfo.id = res.data.id;
                    this.getDateilData(this.baseInfo.id);
                    this.$Modal.success({
                        title: '提交成功',
                        content: '您的额度录入已经提交成功。'
                    });
                    util.closeCurrentPage(this);
                }).catch(res => {
                    console.log(res);
                    this.$Message.error('提交失败！');
                    this.$Spin.hide();
                });
            } else {
                this.$Message.error('提交失败!请确认基本信息及担保信息是否填写正确。');
            }
        },
        // 返回事件
        onBack () {
            util.goBack(this, {});
        },
        // 组合数据
        prepareFormData () {//准备数据
            this.baseInfo.guaranteeType = this.baseInfo.guaranteeTypeData.join(',');
            var res = this.baseInfo.guaranteeTypeData;
            var obj = {
                quotaApply: this.baseInfo,
                quotaUnderwritor: this.underwritorData,
                quotaAgency: this.agencyData
            };
            if (this.baseInfo.paymentRate === 'null' || this.baseInfo.paymentRate == null) {
                this.baseInfo.paymentRate = '';
            }
            for (var i = 0; i < res.length; i++) {
                if (res[i] == 'G20181226000026') {//担保
                    obj.quotaGuarantees = this.guaranteeData;
                } else if (res[i] == 'G20181230000031') {//应收账款抵押
                    obj.quotaPledges = this.pledgesData;
                } else if (res[i] == 'G20181227000030') {//抵押
                    obj.quotaMortgages = this.mortgageData;
                } else if (res[i] == 'G20181226000027') {//存单抵押
                    obj.quotaCertificate = this.cashEpositData;
                } else if (res[i] == 'G20181226000028') {//股权质押
                    obj.quotaStocks = this.stockData;
                }
            }
            return obj;
        },
        //接受返回的 添加承销商信息数据
        underwritorAddList (res) {
            console.log('********************************');
            console.log(res);
            this.underwritorData = res;
        },
        //接受返回的 添加中介机构信息数据
        agencyAddList (res) {
            console.log('********************************');
            console.log(res);
            this.agencyData = res;
        },
        //接受返回的 添加抵押信息数据
        pledgesAddList (res) {
            console.log('********************************');
            console.log(res);
            this.pledgesData = res;
        },
        //接受返回的 添加的担保信息数据
        guaranteeAddList (res) {
            console.log('**********担保信息***********');
            console.log(res);
            this.guaranteeData = res;
        },
        //接受返回的 添加的抵押信息数据
        mortgageAddList (res) {
            console.log('**********抵押信息***********');
            console.log(res);
            this.mortgageData = res;
        },
        //接受返回的 添加的保证金抵押信息数据
        cashEpositAddList (res) {
            console.log('**********保证金抵押信息***********');
            console.log(res);
            this.cashEpositData = res;
        },
        //接受返回的 添加的保股权质押信息数据
        stockAddList (res) {
            console.log('**********股权质押信息***********');
            console.log(res);
            this.stockData = res;
        },
        //接受返回的 基本信息担保方式数据
        baseInfoBack (res) {
            this.guaranteeInfo = false;
            this.pledgeInfo = false;
            this.mortgageInfo = false;
            this.cashEpositInfo = false;
            this.stockInfo = false;
            for (var i = 0; i < res.length; i++) {
                if (res[i] == 'G20181226000026') { // 担保
                    this.guaranteeInfo = true;
                } else if (res[i] == 'G20181230000031') { // 应收账款质押
                    this.pledgeInfo = true;
                } else if (res[i] == 'G20181227000030') { // 抵押
                    this.mortgageInfo = true;
                } else if (res[i] == 'G20181226000027') { // 存单质押
                    this.cashEpositInfo = true;
                } else if (res[i] == 'G20181226000028') { // 股权质押
                    this.stockInfo = true;
                }
            }
        }
    },
    computed: {
        // Tab
        tabs () {
            var obj = [{
                key: 'form',
                label: '额度录入'
            },
                {
                    key: 'quota',
                    label: '额度申请'
                }
            ];

            return obj;
        }
    },
    watch: {},
    mounted () {
        this.init();
    }
};