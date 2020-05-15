<!-- 选择下个节点办理人 -->
<template>
    <div>
        <Modal v-model="show" title="选择办理人">
            <p class="workflowModel">
                <Form label-position="top" :model="form"  ref="form" :rules="modalValidate">
                    <Row >
                        <Col span="24">
                            <CustomFormItem label= "业务部经理"  >
                                 <Select v-model="form.assignee">
                                    <Option v-for="option in form.candidateUserList" :value="option.staffId" :key="option.staffId">
                                        {{option.staffName}}
                                    </Option>
                                </Select>
                            </CustomFormItem>
                        </Col>
                    </Row>
                </Form>
            </p>
            <div slot="footer" style="clear:both">

                <Button type="primary" size="large" @click="confirm">提交</Button>
                <Button type="text"    size="large" @click="cancel">取消</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import util from '../../../../libs/util';
import CustomFormItem from '../../../common/custom-form-item';
export default {
    name: "workflow-default-select",
    components: {
        CustomFormItem
    },
    props: {
    },
    data() {
        return {
            show : false,
            form:{
                name : '',
                assignee:'',
                candidateUserList:[],
            },
           
            modalValidate: {
                    assignee: [
                        { required: true, message: this.$t('message.msg4'), trigger: 'blur' }
                    ],
                },
        }
    },
    methods : {
        //由外部接口调用
        open(projectId) {
            this.form.candidateUserList =[];
            this.show = true;
            util.ajax.get("/business/project/query/projectDeptManager", {
                    params: {
                        projectId: projectId,
                    }
                }).then(res => {
                    this.form.candidateUserList = res.data.projectDeptManageList;
                }).catch(err => {
                    console.log(err);
                });

        },
        confirm() {
            if (!this.form.assignee) {
                this.$Message.error("请正确填写信息。");
                return false;
            }
            const taskUsers = [];
            taskUsers.push({
                    taskDefId: 'business_manager_approval',
                    assigneeVarName: 'business_manager_approval_assignee',
                    assignee: this.form.assignee
            });
            this.$emit('confirm', taskUsers);
            this.show = false;
        },
        cancel() {
                this.show = false;
        },

        validatorModal : function(formName){
            let isValidate = false;
            this.$refs[formName].validate((valid) => { // 根据 ref 检查校验
                isValidate = valid;
            });

            if (!isValidate) {
                this.$Message.error("请正确填写信息。");
                return false;
            }
            return true;
        },
    }
}
</script>
