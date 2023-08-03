<template>
  <div class="ac">
    <el-dialog v-model="addView" title="添加系列" width="50%" :destroy-on-close="true">
      <AddCategory  @cancel="cancel" @finish="finsihAdd"></AddCategory>
    </el-dialog>

    <el-dialog v-model="alterView" title="修改系列" width="50%" style="height: 50%;width: 50%" destroy-on-close>
      <AlterCategory  @cancel="cancel" @finish="alterFinish"  :sd="sId"></AlterCategory>
    </el-dialog>

    <el-button @click="addView = !addView" plain round type="warning" style="margin-top: 5%">添加新系列</el-button>
    <el-table :data="series" :highlight-current-row="true" height="100%" style="width: 100%;margin-top: 3%"
      label-width="30%">
      <el-table-column prop="seriesId" label="编号" sortable></el-table-column>
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
import {ElMessage} from "element-plus";
import {getSeries} from "@/api/BookApi";

let series = ref([])
getSeries().then(Response => {
  series.value = Response.data
})

let addView = ref(false)
let alterView = ref(false)

let cancel = (() => {
  addView.value = false
  alterView.value = false
})

let finsihAdd = (() => {
  addView.value = false
  getSeries().then(Response => {
    series.value = Response.data
  })
  // 本来是因为dialog页面自动缓存，用来强制刷新的，后来发现可以直接使用el-dialog的属性来解决destroy-on-close
  // location.reload()
});


let sId=ref(0)
let alter = ((v) => {
  sId.value=v
  alterView.value = true
})
let alterFinish=(()=>{
  alterView.value = false
  getSeries().then(Response => {
    series.value = Response.data
  })

  // 本来是因为dialog页面自动缓存，用来强制刷新的，后来发现可以直接使用el-dialog的属性来解决destroy-on-close
  //   location.reload()
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
/deep/ .el-table__cell{
  font-size: 20px;
  padding-left: 1%
}

.el-table .el-button {
  margin-left: 10%;
  width: 40%;
}
.ac {
  margin: 3% 5%;
}
</style>
