<template>
    <process-tabs :tabs="tabs" @on-tab-toggle="onTabToggle">
        <div slot="btn">
            <Button v-show="isFormTab && agreeBtn" type="primary" icon="ios-checkmark-outline" @click.prevent="onAgree">
                同意
            </Button>
            <Button v-show="isFormTab && disAgreeBtn" type="primary" icon="ios-circle-outline"
                    @click.prevent="onDisagree">不同意
            </Button>
            <Button v-show="isFormTab && backBtn" type="primary" icon="android-arrow-back" @click.prevent="onPushBack">
                退回
            </Button>
            <Button v-show="isNotifyTab && notifyAddBtnShow" type="primary" icon="android-mail"
                    @click.prevent="onNotify">
                知会
            </Button>
        </div>
        <div slot="form" class="workflow-base">

            <!--导航信息-->
            <guideList></guideList>

            <!--基本信息-->
            <a ref="step01"></a>
            <baseInfoForm slot="content" ref="baseInfo" :init-data="baseInfo" :laywerDisplay="laywerDisplay"
                          :option="option" @baseInfoBack="baseInfoBack">
            </baseInfoForm>

            <!--意向承销商-->
            <a ref="step02"></a>
            <underwritorListTemplate :option="option" ref="underwritor" slot="content">
            </underwritorListTemplate>

            <!--中介机构-->
            <a ref="step03"></a>
            <agencyListTemplate :option="option" ref="agency" slot="content">
            </agencyListTemplate>

            <!--担保信息-->
            <a ref="step04"></a>
            <guaranteeListTemplate v-show="guaranteeInfo" :option="option" ref="guarantee" slot="content">
            </guaranteeListTemplate>

            <!--抵押信息-->
            <a ref="step05"></a>
            <mortgageListTemplate v-show="mortgageInfo" :option="option" ref="mortgage" slot="content">
            </mortgageListTemplate>

            <!--质押信息-->
            <a ref="step06"></a>
            <pledgeListTemplate v-show="pledgeInfo" :option="option" ref="pledge" slot="content">
            </pledgeListTemplate>

            <!--存单质押信息-->
            <a ref="step07"></a>
            <cashEpositListTemplate v-show="cashEpositInfo" :option="option" ref="cashEposit" slot="content">
            </cashEpositListTemplate>

            <!--股权质押信息-->
            <a ref="step08"></a>
            <stockListTemplate v-show="stockInfo" :option="option" ref="stock" slot="content">
            </stockListTemplate>

            <!-- 模态框 同意-->
            <workflow-agree ref="workflowAgree" :task-id="approvalForm.taskId" :variables="{path: 'yes'}"
                            @confirm="agree"/>

            <!-- 模态框 不同意-->
            <workflow-approval ref="workflowUnAgree"
                               :title="this.public.unagree"
                               comment-required
                               @confirm="unagree">
            </workflow-approval>
            <!-- 退回 -->
            <workflow-reject ref="workflowReject"
                             :title="public.reject"
                             :taskId="this.taskId"
                             :instanceId="this.instanceId"
                             @confirm="reject">
            </workflow-reject>
        </div>

        <div slot="process">
            <div>
                <process-flow-chart :processInstanceId="instanceIdTemp"></process-flow-chart>
            </div>
        </div>
        <div slot="notify">
            <Notify ref="notify" slot="content" :option="option"
                    :notifyAddBtnShow.sync="this.notifyAddBtnShow"></Notify>
        </div>
    </process-tabs>
</template>
<script>
    import util from '../../../libs/util';
    import ProcessTabs from '../common/process-tabs';
    import ProcessFlowChart from '../../common/process-flow-chart';
    import baseInfoForm from './components/base-info-form.vue';
    import underwritorListTemplate from './components/underwritor-list-template.vue';
    import agencyListTemplate from './components/agency-list-template.vue';
    import pledgeListTemplate from './components/pledge-list-template.vue';
    import guaranteeListTemplate from './components/guarantee-list-template.vue';
    import mortgageListTemplate from './components/mortgage-list-template.vue';
    import cashEpositListTemplate from './components/cashEposit-list-template.vue';
    import stockListTemplate from './components/stock-list-template.vue';
    import WorkflowCandidateSelect from '../../common/workflow-candidate-select';

    import workflowApproval from '../../common/workflow-approval.vue';
    import workflowReject from '../../common/workflow-reject.vue';
    import WorkflowAgree from '../../common/workflow-agree';
    import guideList from '../../workflow/common/guide-list';
    import Notify from '../common/notify.vue';

    export default {
        name: 'quota_apply_approval',
        components: {
            baseInfoForm,
            underwritorListTemplate,
            agencyListTemplate,
            pledgeListTemplate,
            guaranteeListTemplate,
            mortgageListTemplate,
            cashEpositListTemplate,
            stockListTemplate,
            ProcessTabs,
            ProcessFlowChart,
            WorkflowCandidateSelect,
            workflowApproval,
            workflowReject,
            WorkflowAgree,
            guideList,
            Notify
        },
        data () {
            return {
                agreeBtn: true,
                disAgreeBtn: true,
                backBtn: true,//退回
                instanceId: null,
                taskId: null,
                node: null,
                instanceIdTemp: null,
                laywerDisplay: false,
                tabKey: null,
                notifyAddBtnShow: false,
                guaranteeInfo: false,
                mortgageInfo: false,
                pledgeInfo: false,
                cashEpositInfo: false,
                stockInfo: false,
                baseInfo: {},
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
                //流程提交数据
                approvalForm: {
                    taskId: '',   //任务ID
                    instanceId: '',   //任务流程ID
                    node: '',   //任务流程节点
                    path: '',
                    targetId: '',
                    applyNo: '',//申请编号
                    businessId: '',//额度申请id
                    backDefinitionKey: '', //退回环节时有效
                    approvalComment: '',
                    projectName: '',//任务名称
                    leaseContractNum: '',
                    taskAssigneeList: [],
                    docList: [],
                    title: '',
                },//提交数据
                //公共字段
                public: {
                    hostPmRoleCode: 'xiangMuJingLi',
                    cooperationPmRoleCode: 'xiangMuJingLi',
                    attachmentType: '11',
                    businessType: '02',
                    businessNo: '11223344',
                    agree: '同意',
                    unagree: '不同意',
                    reject: '退回',
                    choose: '人员选择',
                    random: '同意',
                    appraise: '意见',
                    busType: null,
                    appraiseRows: 50,
                    flag: '', //判断操作等
                },
                option: {
                    disabled: true,   //是否禁用
                    button: false,   //按钮是否可用
                },
                isFormTab () {
                    return this.tabKey === 'form';
                },
            };
        },
        methods: {
            // Tab切换工作流标签
            onTabToggle (tabKey) {
                this.tabKey = tabKey;
                if (tabKey === 'process') {
                    this.instanceIdTemp = this.instanceId;
                }
            },
            // 知会
            onNotify () {
                this.$refs.notify.doNotifyModal();
            },
            // 初始化
            init () {
                this.node = this.$route.query.node;
                this.instanceId = this.$route.query.instId;
                this.taskId = this.$route.query.taskId;

                this.approvalForm.taskId = this.$route.query.taskId;
                this.approvalForm.instanceId = this.$route.query.instId;
                this.approvalForm.node = this.$route.query.node;
                this.approvalForm.title = this.$route.query.title;

                this.getDateilData(this.instanceId, this.$route.query.busId);
                this.$refs.notify.initData(this.taskId, this.instanceId, null);
                if (this.node == 'view' || this.node == 'notify') {
                    this.agreeBtn = false;
                    this.disAgreeBtn = false;
                    this.backBtn = false;
                    if (this.$route.query.node == 'notify') {//如果是知会
                        this.notifyAddBtnShow = true;
                    }
                }
            },
            getDateilData (instId, busId) {
                this.$Spin.show(); // 加载loading
                util.ajax.get('/process/wfQuotaApply/quotaApplyData',
                    {
                        params: {instId: instId, id: busId}
                    }).then(res => {
                    console.log('-------res', res);
                    this.$Spin.hide();

                    // 额度申请基本信息
                    this.baseInfo = res.data.quotaApply;
                    this.approvalForm.applyNo = this.baseInfo.id;
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

                        // 律师聘请审批意见是否显示判定
                        let isLaywerApprovaled = true;
                        for (var i = 0; i < this.agencyData.length; i++) {
                            if (this.agencyData[i].orgType == 1) {
                                if (this.agencyData[i].laywerStatus != 2) {
                                    isLaywerApprovaled = false;
                                }
                            }
                        }
                        // 中介机构包含多条律师事务所的情况下，需要所有律师聘请流程都通过，才显示律师聘请审批意见，否则不显示
                        if (isLaywerApprovaled) {
                            this.laywerDisplay = true;
                        }
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

            onAgree () {
                // 如律师聘请流程不通过的情况，需等待律师费审批结果，不允许资金部领导审批通过
                if (this.laywerDisplay) {
                    this.$refs.workflowAgree.open();
                } else {
                    this.$Modal.warning({
                        title: '提交失败',
                        content: '律师聘请审批通过后才可以进行额度审批。'
                    });
                }
            },
            agree (event) {
                this.approvalForm.path = 'yes';
                this.approvalForm.approvalComment = event.comment;
                this.approvalForm.taskAssigneeList = event.taskAssigneeList;
                this.approval();
            },
            // 不同意
            onDisagree () {
                // 如律师聘请流程不通过的情况，需等待律师费审批结果，不允许资金部领导审批通过
                if (this.laywerDisplay) {
                    this.$refs.workflowUnAgree.doShow();
                } else {
                    this.$Modal.warning({
                        title: '提交失败',
                        content: '律师聘请审批通过后才可以进行额度审批。'
                    });
                }
            },
            unagree (comment) {
                this.approvalForm.path = 'end';
                this.approvalForm.approvalComment = comment;
                this.approval();
            },
            //退回
            onPushBack () {
                // 如律师聘请流程不通过的情况，需等待律师费审批结果，不允许资金部领导审批通过
                if (this.laywerDisplay) {
                    this.$refs.workflowReject.doShow();
                } else {
                    this.$Modal.warning({
                        title: '提交失败',
                        content: '律师聘请审批通过后才可以进行额度审批。'
                    });
                }
            },
            reject (rs) {
                this.approvalForm.path = 'back';
                this.approvalForm.backDefinitionKey = rs.key;
                this.approvalForm.approvalComment = rs.approvalComment;
                this.approval();
            },
            approval () {
                console.log('approval form data---', this.approvalForm);
                this.$Spin.show(); // 加载loading
                util.ajax.post('/process/wfQuotaApply/approval', this.approvalForm).then(res => {
                    this.$Spin.hide(); // 关闭loading
                    this.$Modal.success({
                        title: '提交成功',
                        content: '审批操作提交成功。'
                    });
                    util.closeCurrentPage(this);
                }).catch(( err ) => {
                    if(err.response.data){
                        this.$Spin.hide(); // 关闭loading
                        this.$Message.error(err.response.data.reason);
                    }
                    this.$Spin.hide(); // 关闭loading
                });
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
                return [
                    {
                        key: 'form',
                        label: '表单'
                    },
                    {
                        key: 'process',
                        label: '流程审批',
                        // hidden: !this.instanceId,
                    },
                    {
                        key: 'notify',
                        label: '知会'
                    }
                ];
            },
            isNotifyTab () {
                return this.tabKey === 'notify';
            }
        },
        watch: {
            guaranteeType: function (val) {
                console.log('--------------');
                console.log(val);
            },
        },
        mounted () {
            this.init();
        }
    };
</script>



