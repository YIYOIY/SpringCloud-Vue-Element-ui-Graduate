<template>
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
</template>

<script setup>
import HeaderIndex from "@/components/HeaderIndex.vue";
import FooterIndex from "./components/FooterIndex.vue";

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
</script>

<style>
.header {
  height: 10%;
  border-bottom: 2px dashed #94f1d4c4;
}

.content {
  position: absolute;
  width: 95%;
  margin-left: 4%;
  z-index: 1;
}

.footer {
  height: 10%;
  width: 100%;
  top: 90%;
  left: 30%;
  position: absolute;
  z-index: 1;
}

a {
  text-decoration-line: none;
}
</style>
