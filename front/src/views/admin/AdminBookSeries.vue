<template>
  <div class="ac">

  <el-button @click="add()" type="info" style="margin-top: 5%">添加新系列</el-button>
  <el-table :data="series"
            highlight-current-row="true"
            height="400"
            style="width: 100%;margin-top: 3%"
           :row-class-name="rn"
  >
    <el-table-column prop="seriesId" label="编号" width="200px"></el-table-column>
    <el-table-column prop="seriesName" class-name="adminName" label="系列名" width="250px"></el-table-column>
    <el-table-column prop="seriesId" label="操作" width="150px">
      <template v-slot="scope">
        <el-button color="#626aef" round class="el-button" @click="alter(scope.row.seriesId)">修改</el-button>
        <el-button type="danger" round  class="el-button" @click="del(scope.row.seriesId)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  </div>
</template>
<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import axios from 'axios'

let series = ref([])
let router = useRouter()
axios.get('api/bookSeries').then(Response => {
  series.value = Response.data
})


const rn=({row,rowIndex})=>{
  if (rowIndex % 2 !== 0) {
    return 'light-row'
  } else {
    return 'aterrimus-row'
  }
}

let alter = ((v) => {
  console.log(v + "这里是书籍系列修改")
  let id = ref(v)
  console.log(id.value+"给书籍系列修改传递参数")
  router.push({
    name: 'alterBookSeries',
    query: {
      seriesId: id.value
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
    }).catch(Error=>{
      alert(Error.message+"删除失败,请稍后重试!")
    })
  }
})

let add = (() => {
  router.push({
    name: 'addBookSeries'
  })
})
</script>
<style scoped>
/deep/ .adminName .cell {
  padding-left: 30px;
}
.el-table .el-button {
  margin-left: 10%;
  width: 80%;
}
/deep/ .light-row {
  background: #f1f1f5;
}

/deep/ .aterrimus-row {
  background: #eaecef;
}
.ac{
  margin: 2% 20%;
}
.el-button{
  margin-top: 2%;
}
</style>
