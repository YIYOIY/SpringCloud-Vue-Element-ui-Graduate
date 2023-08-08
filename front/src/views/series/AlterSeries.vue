<template>
  <div style="margin: 5% 20%">
    <div style="width: 100%;float: left">
      <el-form size="default" label-position="left" ref="form" width="100%" :model="series">
        <el-form-item label="系列名">
          <el-input type="text" v-model="series.series.seriesName" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-row style="width:100%" justify="space-evenly">
            <el-col span="24">
              <el-button type="danger" plain round @click="alter()">修改</el-button>
            </el-col>
            <el-col span="24">
              <el-button type="success" plain round @click="back()">返回</el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {reactive} from "vue";
import {alterSeries, getSeriesByID} from "@/api/SeriesApi";
import {ElMessage} from "element-plus";
let router = useRouter()

const series = reactive({
  series: {
    seriesName: "",
    seriesId: '',
  }
})

const emit = defineEmits(['finish', 'cancel'])

let props = defineProps(['sd'])

getSeriesByID(props.sd).then(Response => {
  series.series = Response.data
})

let alter = (() => {
  series.seriesId=props.sd
  let Series = JSON.stringify(series.series)
  alterSeries(Series).then(Response => {
    ElMessage.success(Response.message)
    emit('finish')
  }).catch(Error => {
    ElMessage(Error.data.message)
  })
})

let back = (() => {
  emit('cancel')
  // 本来是因为dialog页面自动缓存，用来强制刷新的，后来发现可以直接使用el-dialog的属性来解决destroy-on-close
  // router.go(0)
  // location.reload()
})
</script>

