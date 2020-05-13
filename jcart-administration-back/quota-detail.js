import CustomFormItem from '../../../common/custom-form-item';
import util from '../../../../libs/util';
import SelectLeaseRent from '../../../common/select-lease-rent.vue';
import GuideList from '../../../common/guide-list.vue';
export default {
    name: 'quota-detail',
    components: {
        CustomFormItem,
        SelectLeaseRent,
        GuideList
    },
    props: ['initData', 'registerId'],
    data () {
        return {
            public: {
                contAddendumNum: '', // 合同附表编号
                periodNo: '',        // 期号
            },
            guaranteeInfo: false,
            mortgageInfo: false,
            pledgeInfo: false,
            cashEpositInfo: false,
            stockInfo: false,
            quotaData: {
                // 申请单位
                spvName: '',
                // 意向金额
                intentAmount: '',
                // 担保类型
                guaranteeTypeData: [],
            },
            // 承销商
            underwriterColumns: [
                {
                    key: 'underwritor',
                    title: '承销商名称',
                    render: (h, params) => {
                        return h('div', [
                            h('span', {},
                                util.formateDict(this.getDict('counterParty'), params.row.underwritor)
                            )
                        ]);
                    }
                }
            ],
            underwriterData: [],
            // 中介机构
            agencyColumns: [
                {
                    key: 'orgType',
                    title: '机构类型',
                    render: (h, params) => {
                        return h('div', [
                            h('span', {},
                                util.formateDict(this.getDict('companyType'), params.row.orgType)
                            )
                        ]);
                    }
                },
                {
                    key: 'orgName',
                    title: '机构名称'
                }, {
                    key: 'lawyer',
                    title: '聘请律师'
                }, {
                    key: 'fee',
                    title: '费用',
                    render: (h, params) => {
                        return h('div', [
                            h('span', {}, this.$formatMoney(parseFloat(params.row.fee), 2))
                        ]);
                    }
                }, {
                    key: 'currency',
                    title: '币种',
                    render: (h, params) => {
                        return h('div', [
                            h('span', {},
                                util.formateDict(this.getDict('currency'), params.row.currency)
                            )
                        ]);
                    }
                }, {
                    key: 'planPayDate',
                    title: '预支付日期',
                    render: (h, params) => {
                        return h('div', [
                            h('span', {}, this.$formatDate(params.row.planPayDate))
                        ]);
                    }
                }
            ],
            agencyData: [],
            // 担保信息
            guaranteeColumns: [
                {
                    key: 'registerNo',
                    title: '注册号'
                }, {
                    key: 'guarantorName',
                    title: '担保人'
                }, {
                    key: 'guaranteeAmount',
                    title: '担保金额',
                    render: (h, params) => {
                        return h('div', [
                            h('span', {}, this.$formatMoney(parseFloat(params.row.guaranteeAmount), 2))
                        ]);
                    }
                }
            ],
            guaranteeData: [],
            // 抵押信息
            mortgageColumns: [
                {
                    key: 'registerNo',
                    title: '注册号'
                },
                {
                    key: 'leaseContractNo',
                    title: '租赁合同号'
                },
                {
                    key: 'projectName',
                    title: '项目名称'
                },
                {
                    key: 'asseName',
                    title: '资产名称'
                },
                {
                    key: 'type',
                    title: '资产类别',
                    render: (h, params) => {
                        return h('div', [
                            h('span', {},
                                util.formateDict(this.getDict('assetsType'), params.row.type)
                            )
                        ]);
                    }
                },
                {
                    key: 'asseCount',
                    title: '资产数量'
                }
            ],
            mortgageData: [],
            // 质押信息
            pledgeColumns: [
                {
                    key: 'registerNo',
                    title: '注册号'
                }, {
                    key: 'leaseContractNo',
                    title: '租赁合同号'
                }, {
                    key: 'contAddendumNum',
                    title: '附表编号'
                }, {
                    key: 'projectName',
                    title: '项目名称'
                }, {
                    key: 'periods',
                    title: '期数',
                    render: (h, params) => {
                        return h('div', [
                            h('a', {
                                on: {
                                    click: () => {
                                        this.public.contAddendumNum = params.row.contAddendumNum;
                                        this.public.periodNo = params.row.periodNo;
                                        setTimeout(() => {
                                            this.$refs.leaseRent.isShowModel();
                                        }, 100);
                                    }
                                }
                            }, params.row.periods),
                        ]);
                    }
                }, {
                    key: 'amount',
                    title: '汇总质押本金',
                    render: (h, params) => {
                        return h('div', [
                            h('span', {}, this.$formatMoney(parseFloat(params.row.amount), 2))
                        ]);
                    }
                }, {
                    key: 'rent',
                    title: '汇总质押租金',
                    render: (h, params) => {
                        return h('div', [
                            h('span', {}, this.$formatMoney(parseFloat(params.row.rent), 2))
                        ]);
                    }
                }
            ],
            pledgesData: [],
            // 存单质押信息
            cashEpositColumns: [
                {
                    key: 'registerNo',
                    title: '注册号'
                },
                {
                    key: 'bankDisplay',
                    title: '银行名称'
                }, {
                    key: 'guaranteeAmount',
                    title: '保证金金额',
                    render: (h, params) => {
                        return h('div', [
                            h('span', {}, this.$formatMoney(parseFloat(params.row.guaranteeAmount), 2))
                        ]);
                    }
                }, {
                    key: 'expireDate',
                    title: '到期日',
                    render: (h, params) => {
                        return h('div', [
                            h('span', {}, this.$formatDate(params.row.expireDate))
                        ]);
                    }

                }
            ],
            cashEpositData: [],
            // 股权质押信息
            stockColumns: [
                {
                    key: 'registerNo',
                    title: '注册号'
                },
                {
                    key: 'investor',
                    title: '出资人'
                }, {
                    key: 'contributor',
                    title: '被出资人'
                }, {
                    key: 'investmentRate',
                    title: '出资比例'
                }
            ],
            stockData: [],
            option: {
                disabled: true,   //是否禁用
                button: false,   //按钮是否可用
            },
            guides: [
  	                { refId: 'step01', refName: '发债额度录入' },
  	                { refId: 'step02', refName: '意向承销商' },
  	                { refId: 'step03', refName: '中介机构' },
  	                { refId: 'step04', refName: '担保信息' },
  	                { refId: 'step05', refName: '抵押信息' },
  	                { refId: 'step06', refName: '质押信息' },
  	                { refId: 'step07', refName: '存单质押信息' },
  	                { refId: 'step08', refName: '股权质押信息' },
  	               
  	              ]
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
        // 担保类型
        guaranteeTypeOptions () {
            return this.getDict('guaranteeModeForLoan');
        },
        // 承销费支付方式
        paymentTypeOptions () {
            return this.getDict('bondPaymentType');
        },
        // 是否循环
        loopFlagOptions () {
            return this.getDict('loopFlag');
        }
    },
    watch: {
        registerId: {
            handler (val) {
                this.getDetail(val);
            },
            deep: true
        }
    },
    methods: {
        getDetail (busId) {
            this.$Spin.show(); // 加载loading
            util.ajax.get('/process/quotaApply/getDataById',
                {
                    params: {id: busId}
                }).then(res => {
                this.$Spin.hide();

                // 额度录入基本信息
                this.quotaData = res.data.quotaApply;
                var obj={
                		startDate:this.quotaData.startDate,
                		endDate:this.quotaData.endDate,
                		loopFlag:this.quotaData.loopFlag,
                		surplusAmount:this.quotaData.surplusAmount,
                		registerAmount:this.quotaData.registerAmount,
                }
                this.$emit("quoteStartAndEndDate", obj);//返回额度申请数据
                
                // 起始日期数据格式转换
                if (this.quotaData.startDate != null) {
                    this.quotaData.startDate = this.$formatDate(this.quotaData.startDate);
                }
                // 结束日期数据格式转换
                if (this.quotaData.endDate != null) {
                    this.quotaData.endDate = this.$formatDate(this.quotaData.endDate);
                }
                // 申请日期数据格式转换
                if (this.quotaData.applyDate != null) {
                    this.quotaData.applyDate = this.$formatDate(this.quotaData.applyDate);
                }
                // 担保类型数据格式转换
                if (this.quotaData.guaranteeType != null) {
                    this.quotaData.guaranteeTypeData = this.quotaData.guaranteeType.split(',');

                } else {
                    this.quotaData.guaranteeTypeData = [];
                }

                // 意向承销商
                if (res.data.quotaUnderwritor != null) {
                    this.underwriterData = res.data.quotaUnderwritor;
                }

                // 中介机构
                if (res.data.quotaAgency != null) {
                    this.agencyData = res.data.quotaAgency;
                }

                // 担保信息
                if (res.data.quotaGuarantees != null) {
                    this.guaranteeData = res.data.quotaGuarantees;
                }

                // 抵押信息
                if (res.data.quotaMortgages != null) {
                    this.mortgageData = res.data.quotaMortgages;
                }

                // 质押信息
                if (res.data.quotaPledges != null) {
                    this.pledgesData = res.data.quotaPledges;
                }

                // 存单质押
                if (res.data.quotaCertificate != null) {
                    this.cashEpositData = res.data.quotaCertificate;
                }
                // 股权质押
                if (res.data.quotaStocks != null) {
                    this.stockData = res.data.quotaStocks;
                }

            }).catch(() => {
                this.$Spin.hide();
            });
        },
        // 担保方式监听事件
        selectGuaranteeMode (res) {
            this.guaranteeInfo = false;
            this.pledgeInfo = false;
            this.mortgageInfo = false;
            this.cashEpositInfo = false;
            this.stockInfo = false;
            for (var i = 0; i < res.length; i++) {
                if (res[i] == '02') { // 担保
                    this.guaranteeInfo = true;
                } else if (res[i] == '03') { // 应收账款质押
                    this.pledgeInfo = true;
                } else if (res[i] == '04') { // 抵押
                    this.mortgageInfo = true;
                } else if (res[i] == '05') { // 单质押
                    this.cashEpositInfo = true;
                } else if (res[i] == '06') { // 股权质押
                    this.stockInfo = true;
                }
            }
        },
    }
};