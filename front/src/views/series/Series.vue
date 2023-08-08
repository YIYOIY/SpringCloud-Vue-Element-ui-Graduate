<template>
  <div class="ac">
    <el-dialog v-model="addView" title="添加系列" width="50%" style="height: 50%;width: 50%" :destroy-on-close="true">
      <AddSeries @cancel="cancel" @finish="finsihAdd"></AddSeries>
    </el-dialog>

    <el-dialog v-model="alterView" title="修改系列" width="50%" style="height: 50%;width: 50%" destroy-on-close>
      <AlterSeries @cancel="cancel" @finish="alterFinish" :sd="sId"></AlterSeries>
    </el-dialog>

    <el-button @click="addView = !addView" plain round type="warning" style="margin-top: 5%">添加新系列</el-button>

    <el-button @click="search(searchName)" style="top: 16%;left: 32%;position: absolute" plain round type="info">查找系列</el-button>
    <el-button @click="search('')" style="top: 16%;left: 38%;position: absolute" plain round type="primary">全部系列</el-button>
    <el-form>
      <el-form-item label="系列名称" prop="seriesName" style="top: 16%;left: 15%;position: absolute">
        <el-input type="text" v-model="searchName" autofocus maxlength="20" clearable
                  style="max-width: 200px" placeholder="输入系列名"></el-input>
      </el-form-item>
    </el-form>

    <el-table :data="series" :highlight-current-row="true" height="100%" style="width: 100%;margin-top: 3%"
              label-width="30%">
      <el-table-column prop="id" label="编号" sortable width="200" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="seriesName" label="系列名" width="320"></el-table-column>
      <el-table-column prop="seriesSignTime" label="创建日期" width="320">
        <template v-slot="scope">
          <el-date-picker style="width: 100%;" v-model="scope.row.seriesSignTime" type="datetime" format="YYYY 年 MM 月 DD 日 HH 时 mm 分 ss 秒"></el-date-picker>
        </template>
      </el-table-column>
      <el-table-column prop="version" label="修改日期" width="320">
        <template v-slot="scope">
            <el-date-picker style="width: 100%;" v-model="scope.row.version" type="datetime" format="YYYY 年 MM 月 DD 日 HH 时 mm 分 ss 秒"></el-date-picker>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="操作" width="180">
        <template v-slot="scope">
          <el-button color="#626aef" plain round @click="alter(scope.row.id)">修改</el-button>
          <el-button type="danger" plain round @click="del(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import AddSeries from "@/views/series/AddSeries.vue";
import AlterSeries from "@/views/series/AlterSeries.vue";
import {ref} from "vue";
import {deleteSeries, getAllSeries} from "@/api/SeriesApi";
import {ElMessage, ElNotification} from "element-plus";

let pageNo = ref(1)
let pageSize = ref(5)
let searchName = ref('')

let series = ref([])
getAllSeries('null', pageNo.value, pageSize.value).then(Response => {
  series.value = Response.data.data
})

let search = ((seName) => {
  if (seName === '') searchName.value = null
  getAllSeries(searchName.value, pageNo.value, pageSize.value).then(Response => {
    series.value = Response.data.data
  }).catch((error)=>{
    if (error.data.code === 404) {
      ElNotification({
        title: '未存储',
        message: error.data.message,
        type: "error",
        position: "top-left",
        offset: 100,
      })
      series.value = null
    }
  })
})

let addView = ref(false)
let alterView = ref(false)

let cancel = (() => {
  addView.value = false
  alterView.value = false
})

let finsihAdd = (() => {
  addView.value = false
  getAllSeries(searchName.value, pageNo.value, pageSize.value).then(Response => {
    series.value = Response.data.data
  })
  // 本来是因为dialog页面自动缓存，用来强制刷新的，后来发现可以直接使用el-dialog的属性来解决destroy-on-close
  // location.reload()
});


let sId = ref(0)
let alter = ((v) => {
  sId.value = v
  alterView.value = true
})
let alterFinish = (() => {
  alterView.value = false
  getAllSeries(searchName.value, pageNo.value, pageSize.value).then(Response => {
    series.value = Response.data.data
  })

  // 本来是因为dialog页面自动缓存，用来强制刷新的，后来发现可以直接使用el-dialog的属性来解决destroy-on-close
  //   location.reload()
})

let del = ((v) => {
  if (confirm("确认删除?")) {
    let delSeries = {
      id: v
    }
    let seriesDel = JSON.stringify(delSeries)
    deleteSeries(seriesDel).then(Response => {
      ElMessage.success(Response.message)
      getAllSeries(searchName.value, pageNo.value, pageSize.value).then(Response => {
        series.value = Response.data.data
      })
    }).catch(Error => {
      ElMessage.error(Error.data.message + "删除失败,请稍后重试!")
    })
  }
})
</script>
<style scoped>
.el-table__cell {
  font-size: 20px;
}

.el-table .el-button {
  margin-left: 10%;
  width: 40%;
}

.ac {
  margin: 3% 5%;
}
</style>
