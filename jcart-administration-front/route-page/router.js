const routes = [
    { path: '/product/search',component: ProductSearchRoutePage},
    { path: '/product/create',component: ProductCreateRoutePage},
    { path: '/customer/search',component: CustomerSearchRoutePage},
    { path: '/order/search/:name',component: OrderSearchRoutePage},
    { path: '/administrator/search',component: AdministratorSearchRoutePage}
];

const router = new VueRouter({
    routes: routes
});
