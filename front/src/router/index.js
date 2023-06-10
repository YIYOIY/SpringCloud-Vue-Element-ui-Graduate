import { createRouter, createWebHistory } from "vue-router";

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

export default createRouter({
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
          let password = store.state.userPassword;
          let name = store.state.userName;
          let user = {
            userName: name,
            userPassword: password,
          };
          if (password && name) {
            sessionStorage.setItem("user", user);
            // 一个时间只能登录一个身份时开启下面的
            // sessionStorage.removeItem('admin')
            next();
          } else {
            next(false);
          }
        } else next({ path: "/login" });
      },
    },
    {
      name: "admin",
      path: "/admin",
      component: AdminIndex,
      beforeEnter: (to, from, next) => {
        if (to.name === "admin") {
          let password = store.state.adminPassword;
          let name = store.state.adminName;
          let admin = {
            adminName: name,
            adminPassword: password,
          };
          // 为了方便代码编写先将判断关闭
          // if (password && name) {
          if (1) {
            sessionStorage.setItem("admin", admin);

            // 一个时间只能登录一个身份时开启下面的语句
            // sessionStorage.removeItem('user')
            next();
          } else {
            next(false);
          }
        } else next({ path: "/login" });
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
      props({ query }) {
        return {
          id: query.id,
        };
      },
    },
    {
      name: "alterAdmin",
      path: "/alterAdmin",
      component: AlterAdmin,
      props({ query }) {
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
      props({ query }) {
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
      props({ query }) {
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
      props({ query }) {
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
    },
  ],
});
