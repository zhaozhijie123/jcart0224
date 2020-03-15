var app = new Vue({
  router: router,
  el: '#app',
  data: {
    subMenus: [
      {
        name: '商品管理',
        index: '1',
        icon: 'el-icon-goods',
        menuItems: [
          { name: '商品列表',index: '1-1',route: '/product/search'}
        ]
      },
      {
        name: '客户管理',
        index: '2',
        icon: 'el-icon-s-custom',
        menuItems: [
          { name: '客户列表',index: '2-1',route: '/customer/search'}
        ]
      },
      {
        name: '订单管理',
        index: '3',
        icon: 'el-icon-s-order',
        menuItems: [
          { name: '订单列表',index: '3-1',route: '/order/search'} 
        ]
      },
      {
        name: '用户管理',
        index: '4',
        icon: 'el-icon-user',
        menuItems: [
          { name: '用户列表',index: '4-1',route: '/administrator/search'}
        ]
      }
    ],
    my: [
      { name: "个人信息", route: "/administrator/updateprofile", divided: false },
      { name: "关于", route: "/about", divided: false },
      { name: "退出", route: "/administrator/logout", divided: true }
    ]
  },
  methods: {
    handleMyItemClick(val) {
      this.$router.replace(val);
    }
  }
})
