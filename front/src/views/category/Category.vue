<template>
  <div class="ac">
    <el-dialog v-model="outerVisible" title="添加系列" width="50%">
      <AddCategory :open="outerVisible" @cancel="cancel" @finish="finsihAdd"></AddCategory>
    </el-dialog>
    <!-- <el-button @click="add()" type="info" style="margin-top: 5%">添加新系列</el-button> -->
    <el-button @click="outerVisible = !outerVisible" type="info" style="margin-top: 5%">添加新系列</el-button>

    <el-table :data="series" highlight-current-row="true" height="100%" style="width: 100%;margin-top: 3%"
      label-width="30%" :row-class-name="rn">
      <el-table-column prop="seriesId" label="编号"></el-table-column>
      <el-table-column prop="seriesName" label="系列名"></el-table-column>
      <el-table-column prop="seriesId" label="操作">
        <template v-slot="scope">
          <el-button color="#626aef" round @click="alter(scope.row.seriesId)">修改</el-button>
          <el-button type="danger" round @click="del(scope.row.seriesId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import AddCategory from "./AddCategory.vue";
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from 'axios'
let outerVisible = ref(false)

let cancel = (() => {
  outerVisible.value = false
})


let finsihAdd = (() => {
  outerVisible.value = false
  axios.get('api/bookSeries').then(Response => {
    series.value = Response.data
  })
});


let series = ref([])
let router = useRouter()
axios.get('api/bookSeries').then(Response => {
  series.value = Response.data
})

const rn = ({ row, rowIndex }) => {
  if (rowIndex % 2 !== 0) {
    return 'light-row'
  } else {
    return 'aterrimus-row'
  }
}


let sId = ref(0)

let alter = ((v) => {
  sId.value = v;
  router.push({
    name: 'alterCategory',
    query: {
      seriesId: sId.value
    }
  })
})

let del = ((v) => {
  console.log(v + "这里是书籍系列删除")
  if (confirm("确认删除?")) {
    axios.delete(`api/bookSeries?seriesId=${v}`).then(Response => {
      let message = Response.data
      alert(message)
      axios.get('api/bookSeries').then(Response => {
        series.value = Response.data
      })
    }).catch(Error => {
      alert(Error.message + "删除失败,请稍后重试!")
    })
  }
})

// let add = (() => {
//   router.push({
//     name: 'addCategory'
//   })
// })
</script>
<style scoped>
.ac {
  margin: 3% 5%;
}
</style>
