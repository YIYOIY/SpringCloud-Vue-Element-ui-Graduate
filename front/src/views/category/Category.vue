<template>
  <div class="ac">
    <el-dialog v-model="addView" title="添加系列" width="50%">
      <AddCategory :open="addView" @cancel="cancel" @finish="finsihAdd"></AddCategory>
    </el-dialog>
    <el-dialog v-model="alterView" title="修改系列" width="50%" style="height: 50%;width: 50%">
      <AlterCategory :open="alterView" @cancel="cancel" @finish="alterFinish"></AlterCategory>
    </el-dialog>

    <el-button @click="addView = !addView" plain round type="warning" style="margin-top: 5%">添加新系列</el-button>

    <el-table :data="series" highlight-current-row="true" height="100%" style="width: 100%;margin-top: 3%"
      label-width="30%" :row-class-name="rn">
      <el-table-column prop="seriesId" label="编号"></el-table-column>
      <el-table-column prop="seriesName" label="系列名"></el-table-column>
      <el-table-column prop="seriesId" label="操作">
        <template v-slot="scope">
          <el-button color="#626aef" plain round @click="alter(scope.row.seriesId)">修改</el-button>
          <el-button type="danger" plain round @click="del(scope.row.seriesId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import AddCategory from "./AddCategory.vue";
import AlterCategory from "@/views/category/AlterCategory.vue";
import {ref} from "vue";
import {deleteSeries} from "@/api/CategoryApi";
import {ElLoading, ElMessage} from "element-plus";
import {getSeries} from "@/api/BookApi";
import {provide} from "vue";

let series = ref([])
getSeries().then(Response => {
  series.value = Response.data
})

const rn = ({ row, rowIndex }) => {
  if (rowIndex % 2 !== 0) {
    return 'light-row'
  } else {
    return 'aterrimus-row'
  }
}

let addView = ref(false)
let alterView = ref(false)

let cancel = (() => {
  addView.value = false
  alterView.value = false
})

let finsihAdd = (() => {
  addView.value = false
  location.reload()
});

let sId=ref(0)
provide('seriesId',sId)
let alter = ((v) => {
  sId.value=v
  alterView.value = true
})

let alterFinish=(()=>{
  alterView.value = false

  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  setTimeout(() => {
    location.reload()
    loading.close()
  }, 2000)
})

let del = ((v) => {
  if (confirm("确认删除?")) {
    deleteSeries(v).then(Response => {
      ElMessage.success(Response.message)
      getSeries().then(Response => {
        series.value = Response.data
      })
    }).catch(Error => {
      ElMessage.error(Error.data.message + "删除失败,请稍后重试!")
    })
  }
})
</script>
<style scoped>
.ac {
  margin: 3% 5%;
}
</style>
