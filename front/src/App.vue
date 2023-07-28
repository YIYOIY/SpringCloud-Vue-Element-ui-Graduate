<template>
  <div STYLE="width: 100%;height: 100%">


  <div class="header">
    <HeaderIndex />
  </div>

  <div class="content">
    <!--    <router-view></router-view>-->
    <router-view v-slot="{ Component, route }">
      <!--        <component :is="Component" :key="route.name" v-if="route.meta.keepAlive"></component>-->
      <keep-alive>
        <component :is="Component" :key="route.name" v-if="route.meta.keepAlive"></component>
      </keep-alive>
      <component :is="Component" :key="route.name" v-if="!route.meta.keepAlive" />
    </router-view>
  </div>

  <el-backtop :right="20" :bottom="30">
    <div style="
        height: 100%;
        width: 100%;
        background-color: white;
        box-shadow: var(--el-box-shadow-lighter);
        text-align: center;
        line-height: 40px;
        color: rgb(109, 229, 151);
        border-radius:15px;
      ">
      UP
    </div>
  </el-backtop>

  <div class="footer">
    <FooterIndex />
  </div>
  </div>
</template>

<script setup>
import HeaderIndex from "@/components/HeaderIndex.vue";
import FooterIndex from "./components/FooterIndex.vue";
import store from "@/store"
import {onUnmounted} from "vue";

  // 在页面加载时读取sessionStorage里的状态信息
  if (sessionStorage.getItem('store')) {
    // 存储状态
    store.replaceState(Object.assign({}, store.state, JSON.parse(sessionStorage.getItem('store'))))
  }
  // 在页面刷新时将vuex里的信息保存到sessionStorage里
  window.addEventListener('beforeunload', () => {
    sessionStorage.setItem('store', JSON.stringify(store.state))
  })


const debounce = (fn, delay) => {
  let timer = null;
  return function () {
    let context = this;
    let args = arguments;
    clearTimeout(timer);
    timer = setTimeout(function () {
      fn.apply(context, args);
    }, delay);
  }
}

const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
  constructor(callback) {
    callback = debounce(callback, 16);
    super(callback);
  }
}

onUnmounted(() => {
  sessionStorage.removeItem('token')
  sessionStorage.removeItem('store')
})
</script>

<style>
.header {
  width: 98%;
  height: 10%;
  border-bottom: 2px dashed #94f1d4c4;
}

.content {
  position: absolute;
  width: 95%;
  height: 85%;
  margin-left: 4%;
  z-index: 1;
}

.footer {
  bottom: 2%;
  height: 5%;
  width: 98%;
  position: absolute;
  z-index: -1;
}

a {
  text-decoration-line: none;
}
</style>
