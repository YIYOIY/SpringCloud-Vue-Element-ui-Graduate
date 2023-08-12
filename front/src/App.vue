<template>
  <div class="header">
    <!--    菜单也可以单走横着的方式-->
    <div>
      <NavigationHorizontal/>
    </div>
  </div>

  <router-view v-slot="{ Component, route }">
    <!--        <component :is="Component" :key="route.name" v-if="route.meta.keepAlive"></component>-->
    <keep-alive>
      <component :is="Component" :key="route.name" v-if="route.meta.keepAlive"></component>
    </keep-alive>
      <component :is="Component" :key="route.name" v-if="!route.meta.keepAlive"/>
  </router-view>

  <el-backtop :right="20" :bottom="20">
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
    <FooterBar/>
  </div>

</template>

<script setup>
import NavigationHorizontal from "@/components/NavgationHorizontal.vue";
import store from "@/store"
import {onUnmounted} from "vue";
import FooterBar from "@/components/FooterBar.vue";


// 在页面加载时读取sessionStorage里的状态信息
if (sessionStorage.getItem('store')) {
  // 存储状态
  store.replaceState(Object.assign({}, store.state, JSON.parse(sessionStorage.getItem('store'))))
}
// 在页面刷新时将vuex里的信息保存到sessionStorage里
window.addEventListener('beforeunload', () => {
  sessionStorage.setItem('store', JSON.stringify(store.state))
})

// 防止element的bug
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
})
</script>

<style>
a {
  text-decoration-line: none;
}

.header {
  width: 100%;
  z-index: 2;
  position: fixed;
}

.footer {
  bottom: 0;
  width: 100%;
  z-index: 2;
  position: fixed;
}
</style>
