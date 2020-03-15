const routes = [
    { path: '/administrator/updateprofile', component: AdministratorUpdateProfileRoutePage },

    { path: '/product/search',component: ProductSearchRoutePage},
    { path: '/product/create',component: ProductCreateRoutePage},
    { path: '/product/update/:productId',component: ProductUpdateRoutePage},


    { path: '/customer/search',component: CustomerSearchRoutePage},


    { path: '/order/search',component: OrderSearchRoutePage},

    
    { path: '/administrator/search',component: AdministratorSearchRoutePage}
];

const router = new VueRouter({
    routes: routes
});
