import {createRouter, createWebHistory} from "vue-router";
import {checkToken} from "@/api/LoginApi";
import store from "@/store";
import {ElLoading, ElMessage} from "element-plus";

import Book from "@/views/book/BookIndex.vue";
import AlterBooks from "@/views/book/AlterBooks.vue";
import AddBook from "@/views/book/AddBook.vue";
import BookInformation from "@/views/book/BookInformation.vue";
import AdminBooks from "@/views/book/AdminBooks.vue";
import ShopkeeperBooks from "@/views/book/ShopkeeperBooks.vue";

import UserIndex from "@/views/user/UserIndex.vue";
import AlterUser from "@/views/user/AlterUser.vue";
import AddUser from "@/views/user/AddUser.vue";
import AdminUsers from "@/views/user/AdminUsers.vue";

import AdminIndex from "@/views/admin/AdminIndex.vue";
import AlterAdmin from "@/views/admin/AlterAdmin.vue";
import AddAdmin from "@/views/admin/AddAdmin.vue";
import Admins from "@/views/admin/Admins.vue";

import Login from "@/views/Login.vue";

import AddSeries from "@/views/series/AddSeries.vue";
import AlterSeries from "@/views/series/AlterSeries.vue";
import Series from "@/views/series/Series.vue";

import AdminOrder from "@/views/order/AdminOrder.vue";
import UserOrder from "@/views/order/UserOrder.vue";
import OrderConfirm from "@/views/order/OrderConfirm.vue";

import ShopkeeperIndex from "@/views/shopkeeper/ShopkeeperIndex.vue";
import AlterShopkeeper from "@/views/shopkeeper/AlterShopkeeper.vue";
import AdminShopkeepers from "@/views/shopkeeper/AdminShopkeepers.vue";
import AddShopkeeper from "@/views/shopkeeper/AddShopkeeper.vue";
import ShopKeeperOrder from "@/views/order/ShopKeeperOrder.vue";


let router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            name: "book",
            path: "/book",
            alias: "/",
            component: Book,
        },
        {
            name: "user",
            path: "/user",
            component: UserIndex,
            beforeEnter: (to, from, next) => {
                if (to.name === "user") {
                    if (store.state.isUser) {
                        next();
                    } else {
                        next(false);
                    }
                } else next({path: "/login"});
            },
        },
        {
            name: "admin",
            path: "/admin",
            component: AdminIndex,
            beforeEnter: (to, from, next) => {
                if (to.name === "admin") {
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                } else next({path: "/login"});
            },
        },
        {
            name: "shopkeeper",
            path: "/shopkeeper",
            component: ShopkeeperIndex,
            beforeEnter: (to, from, next) => {
                if (to.name === "shopkeeper") {
                    if (store.state.isShopkeeper) {
                        next();
                    } else {
                        next(false);
                    }
                } else next({path: "/login"});
            },
        },
        {
            name: "login",
            path: "/login",
            component: Login,
            meta: {
                keepAlive: false,
            },
        },
        {
            name: "information",
            path: "/information",
            component: BookInformation,
            props({query}) {
                return {
                    id: query.id,
                };
            },
        },
        {
            name: "admins",
            path: '/admins',
            component: Admins,
            beforeEnter: (to, from, next) => {
                if (to.name === "admins") {
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "alterAdmin",
            path: "/alterAdmin",
            component: AlterAdmin,
            props({query}) {
                return {
                    adminId: query.adminId,
                };
            },
            beforeEnter: (to, from, next) => {
                if (to.name === "alterAdmin") {
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "addAdmin",
            path: "/addAdmin",
            component: AddAdmin,
            beforeEnter: (to, from, next) => {
                if (to.name === "addAdmin") {
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "adminUsers",
            path: "/adminUsers",
            component: AdminUsers,
            beforeEnter: (to, from, next) => {
                if (to.name === "adminUsers") {
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "alterUser",
            path: "/alterUser",
            component: AlterUser,
            props({query}) {
                return {
                    userId: query.userId,
                };
            },
            beforeEnter: (to, from, next) => {
                if (to.name === "alterUser") {
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "addUser",
            path: "/addUser",
            component: AddUser,
        },
        {
            name: "adminShopkeepers",
            path: "/adminShopkeepers",
            component: AdminShopkeepers,
            beforeEnter: (to, from, next) => {
                if (to.name === "adminShopkeepers") {
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "alterShopkeeper",
            path: "/alterShopkeeper",
            component: AlterShopkeeper,
            props({query}) {
                return {
                    shopkeeperId: query.shopkeeperId,
                };
            },
            beforeEnter: (to, from, next) => {
                if (to.name === "alterShopkeeper") {
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "addShopkeeper",
            path: "/addShopkeeper",
            component: AddShopkeeper,
            beforeEnter: (to, from, next) => {
                if (to.name === "addShopkeeper") {
                    if (store.state.isAdmin || store.state.isUser) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "adminBooks",
            path: "/adminBooks",
            component: AdminBooks,
            beforeEnter: (to, from, next) => {
                if (to.name === "adminBooks") {
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "shopkeeperBooks",
            path: "/shopkeeperBooks",
            component: ShopkeeperBooks,
            beforeEnter: (to, from, next) => {
                if (to.name === "shopkeeperBooks") {
                    if (store.state.isShopkeeper) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "alterBooks",
            path: "/alterBooks",
            component: AlterBooks,
            props({query}) {
                return {
                    bookId: query.bookId,
                };
            },
            beforeEnter: (to, from, next) => {
                if (to.name === "alterBooks") {
                    if (store.state.isAdmin || store.state.isShopkeeper) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "addBook",
            path: "/addBook",
            component: AddBook,
            beforeEnter: (to, from, next) => {
                if (to.name === "addBook") {
                    if (store.state.isAdmin || store.state.isShopkeeper) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "series",
            path: "/series",
            component: Series,
            beforeEnter: (to, from, next) => {
                if (to.name === "series") {
                    if (store.state.isAdmin || store.state.isShopkeeper) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "alterSeries",
            path: "/alterSeries",
            component: AlterSeries,
            props({query}) {
                return {
                    seriesId: query.seriesId,
                };
            },
            beforeEnter: (to, from, next) => {
                if (to.name === "alterSeries") {
                    if (store.state.isAdmin || store.state.isShopkeeper) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "addSeries",
            path: "/addSeries",
            component: AddSeries,
            beforeEnter: (to, from, next) => {
                if (to.name === "addSeries") {
                    if (store.state.isAdmin || store.state.isShopkeeper) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "adminOrder",
            path: "/adminOrder",
            component: AdminOrder,
            beforeEnter: (to, from, next) => {
                if (to.name === "adminOrder") {
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "shopkeeperOrder",
            path: "/shopkeeperOrder",
            component: ShopKeeperOrder,
            beforeEnter: (to, from, next) => {
                if (to.name === "shopkeeperOrder") {
                    if (store.state.isShopkeeper) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "userOrder",
            path: "/userOrder",
            component: UserOrder,
            beforeEnter: (to, from, next) => {
                if (to.name === "userOrder") {
                    if (store.state.isUser) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        }, {
            name: 'orderConfirm',
            path: '/orderConfirm',
            component: OrderConfirm,
            beforeEnter: (to, from, next) => {
                if (to.name === "orderConfirm") {
                    if (store.state.isUser || store.state.isShopkeeper|| store.state.isAdmin) {
                        next();
                    } else {
                        next({path: '/login'});
                    }
                }
            },
            props({query}) {
                return {
                    orderId: query.orderId,
                }
            }
        }
    ],
});

router.beforeEach((to, from, next) => {
    const loadingInstance = ElLoading.service({
        fullscreen: true,
        lock: true,
        text: '游弋，一次实践',
        background: 'rgb(40,39,37)',
    })
    setTimeout(() => {
        loadingInstance.close()
    }, 1000)

    // 页面加载效果，这个是黑色的
    // const loading = ElLoading.service({
    //   lock: true,
    //   text: 'Loading',
    //   background: 'rgba(0, 0, 0, 0.7)',
    // })
    // setTimeout(() => {
    //   loading.close()
    // }, 2000)

    if (to.path === '/login') { // 如果跳转登录页面,则移除token
        store.commit('clearState')
        next()
    } else if (to.path === '/' || to.path === '/book' || to.name === 'addUser' || to.name === 'information') {
        next()
    } else {
        let token = sessionStorage.getItem('token');
        if (token === null || token === '') { //token不存在页跳转到登录页面
            router.replace({path: '/login'})
        } else {
            // 检验token是否正确
            checkToken(token).then((response) => {
                if (response.code !== 200) {
                    ElMessage.error('检验失败' + response)
                    router.replace({path: '/login'}) // 如果token失效,返回到登录页面
                } else {
                    sessionStorage.setItem("token", response.data);
                    next();
                }
            }).catch(Error => {
                if (Error.code !== 200) {
                    ElMessage.error('检验失败' + Error.data.message)
                    router.replace({path: '/login'}) // 如果token失效,返回到登录页面
                    next({path: '/login'})
                }
            })
        }
    }
})

export default router


