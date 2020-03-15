const routes = [
    { path: '/administrator/updateprofile', component: AdministratorUpdateProfileRoutePage },

    { path: '/product/search',component: ProductSearchRoutePage},
    { path: '/product/create',component: ProductCreateRoutePage},
    { path: '/product/update/:productId',component: ProductUpdateRoutePage},


    { path: '/customer/search',component: CustomerSearchRoutePage},
    { path: '/customer/show/:customerId', component: CustomerShowRoutePage },
    { path: '/address/index/:customerId', component: AddressIndexRoutePage },


    { path: '/order/search',component: OrderSearchRoutePage},

    
    { path: '/administrator/search',component: AdministratorSearchRoutePage}
];

const router = new VueRouter({
    routes: routes
});
