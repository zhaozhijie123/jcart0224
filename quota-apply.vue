<template>
    <process-tabs :tabs="tabs" @on-tab-toggle="onTabToggle">
        <div slot="btn">
            <Button v-show="saveBtn" type="primary" icon="ios-checkmark-outline" @click.prevent="onSave">保存</Button>
            <Button v-show="submitBtn" type="primary" icon="ios-checkmark-outline" @click.prevent="onSubmit">
                {{submitBtnTitle}}
            </Button>
            <Button v-show="backBtn" type="primary" icon="ios-checkmark-outline" @click.prevent="onBack">返回</Button>
            <Button v-show="isNotifyTab && notifyAddBtnShow" type="primary" icon="android-mail"
                    @click.prevent="onNotify">知会
            </Button>
        </div>
        <div slot="form" class="workflow-base">

            <!--审批办理人选择-->
            <workflow-candidate-select ref="candidateSelect"
                                       :variables="this.pathVariables"
                                       process-definition-key="QuotaApply"
                                       @on-ok="onCandidateSelected"></workflow-candidate-select>

            <!--导航信息-->
            <guideList></guideList>

            <!--基本信息-->
            <a ref="step01"></a>
            <baseInfoForm slot="content" ref="baseInfo" :init-data="baseInfo" :option="option"
                          @baseInfoBack="baseInfoBack">
            </baseInfoForm>

            <!--意向承销商-->
            <a ref="step02"></a>
            <underwritorListTemplate :option="option" ref="underwritor" slot="content"
                                     @underwritorBackData="underwritorAddList">
            </underwritorListTemplate>

            <!--中介机构-->
            <a ref="step03"></a>
            <agencyListTemplate :option="option" ref="agency" slot="content" @agencyBackData="agencyAddList">
            </agencyListTemplate>

            <!--担保信息-->
            <a ref="step04"></a>
            <guaranteeListTemplate v-show="guaranteeInfo" :option="option" ref="guarantee" slot="content"
                                   @guaranteeBackData="guaranteeAddList">
            </guaranteeListTemplate>

            <!--抵押信息-->
            <a ref="step05"></a>
            <mortgageListTemplate v-show="mortgageInfo" :option="option" ref="mortgage" slot="content"
                                  @mortgageBackData="mortgageAddList">
            </mortgageListTemplate>

            <!--质押信息-->
            <a ref="step06"></a>
            <pledgeListTemplate v-show="pledgeInfo" :option="option" ref="pledge" slot="content"
                                @pledgesBackData="pledgesAddList">
            </pledgeListTemplate>

            <!--存单质押信息-->
            <a ref="step07"></a>
            <cashEpositListTemplate v-show="cashEpositInfo" :option="option" ref="cashEposit" slot="content"
                                    @cashEpositBackData="cashEpositAddList">
            </cashEpositListTemplate>

            <!--股权质押信息-->
            <a ref="step08"></a>
            <stockListTemplate v-show="stockInfo" :option="option" ref="stock" slot="content"
                               @stockBackData="stockAddList">
            </stockListTemplate>
        </div>

        <div slot="process">
            <div>
                <process-flow-chart :processInstanceId="instanceIdTemp" :processKey="processKey"></process-flow-chart>
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
    import guideList from '../../workflow/common/guide-list';
    import Notify from '../common/notify.vue';

    export default {
        name: 'quota_apply',
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
            guideList,
            Notify
        },
        data () {
            return {
                processKey: 'QuotaApply',
                pathVariables: {path: 'fund_dept'},
                instanceId: null,
                taskId: null,
                node: null,
                instanceIdTemp: null,
                laywerDisplay: false,
                tabKey: null,
                notifyAddBtnShow: false,
                submitBtnTitle: '提交',
                saveBtn: true,
                submitBtn: true,
                backBtn: true,
                guaranteeInfo: false,
                mortgageInfo: false,
                pledgeInfo: false,
                cashEpositInfo: false,
                
                stockInfo: false,
                baseInfo: {
                    // 申请单位
                    spvName: '',
                    // 意向金额
                    intentAmount: '',
                    // 担保类型
                    guaranteeTypeData: ['G20181226000029'],
                    // 律师审批意见
                    laywerApproval: ''
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
                option: {
                    disabled: false,   //是否禁用
                    button: true,   //按钮是否可用
                }
            };
        },
        methods: {
            // 初始化
            init () {
                // 额度申请取消业务部审批节点
                // let deptInfo = util.getLoginUser();
                // if (deptInfo.depts != null && deptInfo.depts.length > 0) {
                //     let deptCode = deptInfo.depts[0].deptCode;
                //     if (deptCode == 'ADMN-7ED31A') {
                //         this.pathVariables = {path: 'fund_dept'};
                //     }
                // }
                this.instanceId = this.$route.query.instId;
                this.taskId = this.$route.query.taskId;
                this.node = this.$route.query.node;
                this.$refs.notify.initData(this.taskId, this.instanceId, null);
                if (this.node != null) {
                    if (this.node == 'draft') {//退回拟稿
                        this.submitBtnTitle = '重新提交';
                        this.saveBtn = false;
                        this.submitBtn = true;
                        this.backBtn = false;
                    } else if (this.node == 'view' || this.node == 'notify') {
                        this.option.disabled = true;  //是否禁用
                        this.option.button = false; //按钮是否可用
                        this.saveBtn = false;
                        this.submitBtn = false;
                        this.backBtn = false;

                        if (this.node == 'notify') {
                            this.notifyAddBtnShow = true;
                        }
                    }
                    this.getDateilData(this.instanceId, this.$route.query.busId);
                } else {
                    if (this.$route.query.busId != undefined) {//拟稿
                        this.getDateilData('', this.$route.query.busId);
                    }
                    this.backBtn = false;
                }
            },
            parserDate(date) {
                var t = Date.parse(date)
                if (!isNaN(t)) {
                    return new Date(Date.parse(date.replace(/-/g, '/')))
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
                    // 申请日期数据格式转换
                    if (this.baseInfo.applyDate != null) {
                        this.baseInfo.applyDate = this.parserDate(this.$formatDate(this.baseInfo.applyDate));
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

            //加载数据
            search (applyId) {
                this.$Spin.show(); // 加载loading
                util.ajax.get('/process/wfQuotaApply/quotaApplyData', {
                    params: {
                        applyId: applyId
                    }
                }).then(res => {
                    this.$Spin.hide(); // 关闭loading
                    this.quotaItem = res.data.quotaApply;
                    this.quotaItem.guaranteeType = this.quotaItem.guaranteeType.split(',');
                }).catch((/* err */) => {
                    this.$Spin.hide(); // 关闭loading
                });
            },

            //保存
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
                    let url = '/process/wfQuotaApply/save';
                    if(this.$route.query.applicationId){
                        url = url + '?appId='+this.$route.query.applicationId;
                    }
                    console.log('url:'+url);
                    util.ajax.post(url, this.prepareFormData()).then(res => {
                        this.$Spin.hide();
                        console.log('save back data', res);
                        this.baseInfo.id = res.data.id;
                        this.$Modal.success({
                            title: '保存成功',
                            content: '您的额度申请已经保存成功。'
                        });
                    }).catch((data) => {
                        this.$Modal.error({
                            title: "提交失败",
                            content: data.response.data.reason
                        });
                        this.$Spin.hide();
                    });
                } else {
                    this.$Message.error('保存失败!请确认基本信息及担保信息是否填写正确。');
                }
            },
            // 表单提交
            onSubmit () {
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
                    this.$refs.candidateSelect.open();
                } else {
                    this.$Message.error('保存失败!请确认申请表单及担保信息是否填写正确。');
                }
            },
            // 流程提交
            onCandidateSelected (taskAssigneeList) {
                console.log('Before Submit');
                const reqData = this.prepareFormData();
                reqData.taskAssigneeList = taskAssigneeList;
                console.log(reqData);

                this.$Spin.show(); // 加载loading
                let url = '/process/wfQuotaApply/commit';
                if(this.$route.query.applicationId){
                    url = url + '?appId='+this.$route.query.applicationId;
                }
                console.log('url:'+url);
                util.ajax.post(url, reqData).then(res => {
                    this.$Spin.hide();

                    this.$Modal.success({
                        title: '提交成功',
                        content: '您的申请已经提交成功。'
                    });
                    util.closeCurrentPage(this);
                }).catch(data => {
                    this.$Modal.error({
                        title: "提交失败",
                        content: data.response.data.reason
                    });
                    this.$Spin.hide();
                });
            },
            // 组合数据
            prepareFormData () {
                if (this.instanceId != null) {
                    this.baseInfo.procInstanceId = this.instanceId;
                    this.baseInfo.procTaskId = this.taskId;
                }
                if (this.baseInfo.paymentRate === 'undefined' || this.baseInfo.paymentRate == null) {
                    this.baseInfo.paymentRate = '';
                }
                this.baseInfo.guaranteeType = this.baseInfo.guaranteeTypeData.join(',');
                var res = this.baseInfo.guaranteeTypeData;
                var obj = {
                    quotaApply: this.baseInfo,
                    quotaUnderwritor: this.underwritorData,
                    quotaAgency: this.agencyData
                };
                for (var i = 0; i < res.length; i++) {
                    if (res[i] == 'G20181226000026') {//担保
                        obj.quotaGuarantees = this.guaranteeData;
                    } else if (res[i] == 'G20181230000031') {//应收账款质押
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
            //接受返回的 添加意向承销商信息数据
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
            },
            // 返回
            onBack () {
                util.goBack(this, {});
            },
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
        mounted () {
            this.init();
        }
    };
</script>



