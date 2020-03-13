const routes = [
    { path: '/product/search',component: ProductSearchRoutePage},
    { path: '/customer/search',component: CustomerSearchRoutePage},
    { path: '/order/search',component: OrderSearchRoutePage},
    { path: '/administrator/search',component: AdministratorSearchRoutePage}
];

const router = new VueRouter({
    routes: routes
});
