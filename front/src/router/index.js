import {createRouter, createWebHistory} from "vue-router";
import {checkToken} from "@/api/LoginApi";

import Book from "@/views/book/BookIndex.vue";
import AlterBooks from "@/views/book/AlterBooks.vue";
import AddBook from "@/views/book/AddBook.vue";
import BookInformation from "@/views/book/BookInformation.vue";
import AdminBooks from "@/views/book/AdminBooks.vue";

import UserIndex from "@/views/user/UserIndex.vue";
import AlterUser from "@/views/user/AlterUser.vue";
import AddUser from "@/views/user/AddUser.vue";
import AdminUsers from "@/views/user/AdminUsers.vue";

import AdminIndex from "@/views/admin/AdminIndex.vue";
import AlterAdmin from "@/views/admin/AlterAdmin.vue";
import AddAdmin from "@/views/admin/AddAdmin.vue";

import Login from "@/views/Login.vue";
import store from "@/store";


import AddCategory from "@/views/category/AddCategory.vue";
import AlterCategory from "@/views/category/AlterCategory.vue";
import Category from "@/views/category/Category.vue";

import AdminOrder from "@/views/order/AdminOrder.vue";
import UserOrder from "@/views/order/UserOrder.vue";
import OrderConfirm from "@/views/order/OrderConfirm.vue";
import {ElLoading, ElMessage} from "element-plus";


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
                    if (store.state.isAdmin || store.state.isUser) {
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
                    if (store.state.isAdmin) {
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
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "category",
            path: "/category",
            component: Category,
            beforeEnter: (to, from, next) => {
                if (to.name === "category") {
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "alterCategory",
            path: "/alterCategory",
            component: AlterCategory,
            props({query}) {
                return {
                    seriesId: query.seriesId,
                };
            },
            beforeEnter: (to, from, next) => {
                if (to.name === "alterCategory") {
                    if (store.state.isAdmin) {
                        next();
                    } else {
                        next(false);
                    }
                }
            },
        },
        {
            name: "addCategory",
            path: "/addCategory",
            component: AddCategory,
            beforeEnter: (to, from, next) => {
                if (to.name === "addCategory") {
                    if (store.state.isAdmin) {
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
                    if (store.state.isUser || store.state.isAdmin) {
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
        text: '游弋专用加载，其实数据早就好了，就为了让你等！',
        background: 'rgb(255,176,17)'
    })
    setTimeout(() => {
        loadingInstance.close()
    }, 2000)

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
                    next();
                }
            }).catch(Error => {
                if (Error.data.code !== 200) {
                    ElMessage.error('检验失败' + Error.data.message)
                    router.replace({path: '/login'}) // 如果token失效,返回到登录页面
                    next({path: '/login'})
                }
            })
        }
    }
})

export default router
