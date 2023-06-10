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


  <div class="footer">

  </div>
</template>

<script setup>
import HeaderIndex from "@/components/HeaderIndex.vue";

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
}

a {
  text-decoration-line: none;
}
</style>
