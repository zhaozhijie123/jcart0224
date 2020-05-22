import CustomFormItem from "../../../common/custom-form-item";
import SelectCompanyTree from "../../../common/select-companyTree.vue";
import util from '../../../../libs/util';
    export default {
        name: 'base-info-form',
        components: {
            CustomFormItem,
            SelectCompanyTree
        },
        props: ["initData","option","closeTarget"],
        data() {
            return {
             
                baseInfo: {
                },
                guaranteeModeOptions:[],
                companyModelShow:false,
                validateRules: {
                	lender: [
                        { required: true, message: '请输入贷款人', trigger: 'change' }
                    ],
                    spvEnName: [
                             { required: true, message: '请选择所属SPV', trigger: 'change' }
                         ],
                    loanAmount: [
                             { required: true, message: '请输入借款金额', trigger: 'change' }
                          ],
                }
            };
        },
        computed: {
        	loanTypeOptions(){
        		 return this.getDict('loanType');
        	},
        	forecastLoanLimitOptions(){
        		 return this.getDict('forecastLoanLimit');
        	},
        	loanMethodOptions(){
        		 return this.getDict('loanMethod');
        	},
        	loanModeOptions(){
        		 return this.getDict('loanMode');
        	},
            currencyOptions () {
                return this.getDict('currency');
            },
            
        },
        watch: {
            initData: {
                handler (val) {
                    this.baseInfo = val;
                },
                deep: true
            },
        },
        created () {
            this.baseInfo = this.initData;
            util.ajax.get('/fund/guaranteeMode/listall').then(res => {
	        	   let data=res.data;
	        	   for(var i=0;i<data.length;i++){
	        		   this.guaranteeModeOptions.push({
	        			   dictCode :data[i].guaranteeNumber,
	        			   dictValueZhCn  :data[i].guaranteeName
	        		   })
	        	   }
	        }).catch(() => {
	                     
	        });
        },
        methods: {
        	 onCompanySelect () {
                 if (this.option.disabled) {
                     return;
                 }
                 this.companyModelShow = true;
             },
             onFinancialTypeSelect(){
            	 
             },
             // 选择SPV组件的回调(对话框显示状态)
             companySelectStatusCallback(showStatus) {
                 this.companyModelShow = showStatus;
             },
             // 选择SPV组件的回调(数据)
             companySelectDataCallback(data) {
                 const company = JSON.parse(data);
                 this.baseInfo.spvEnName = company.selectCompanyName[0];
                 this.baseInfo.spvId = company.selectCompanyCode[0];
             },
             isValidate() { //验证是否通过
		           let isValidate = false;
		            this.$refs['baseForm'].validate((valid) => { // 根据 ref 检查校验
		                isValidate = valid;
		            });
		            return isValidate;
		      },
		      selectGuaranteeMode(data){
		    	  this.$emit("baseInfoBack", data);
		      },
		      back(){
		    	  util.closeCurrentPage(this);
		      }
        }
    };