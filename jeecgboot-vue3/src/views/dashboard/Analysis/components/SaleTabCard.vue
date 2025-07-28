<template>
  <a-card :loading="loading" :bordered="false" :body-style="{ padding: '0' }">
    <div class="salesCard">
      <a-tabs default-active-key="1" size="large" :tab-bar-style="{ marginBottom: '24px', paddingLeft: '16px' }">
        <a-tab-pane loading="true" tab="TOP10" key="1">
          <a-row>
            <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
              <!-- <Bar :chartData="barData" :option="{ title: { text: '', textStyle: { fontWeight: 'lighter' } } }" height="40vh" :seriesColor="seriesColor"  /> -->
              <LineMulti :chartData="lineMultiData" height="53vh" type="line" :option="{ legend: { top: 'top' } }"></LineMulti>

            </a-col>
            <!-- <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
              <RankList title="门店销售排行榜" :list="rankList" />
            </a-col> -->
          </a-row>
        </a-tab-pane>

        <a-tab-pane tab="持仓" key="2">
          <a-row>
            <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
              <LineMulti :chartData="lineMultiDataHolder" height="53vh" type="line" :option="{ legend: { top: 'top' } }"></LineMulti>
            </a-col>
          </a-row>
        </a-tab-pane>
        <a-tab-pane tab="Top30" key="3">
          <a-row>
            <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
              <LineMulti :chartData="lineMultiData3" height="53vh" type="line" :option="{ legend: { top: 'top' } }"></LineMulti>
            </a-col>
          </a-row>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-card>
</template>
<script lang="ts" setup>
import { ref, computed } from 'vue';
import Bar from '/@/components/chart/Bar.vue';
import RankList from '/@/components/chart/RankList.vue';
import { useRootSetting } from '/@/hooks/setting/useRootSetting';
import LineMulti from '/@/components/chart/LineMulti.vue';
import { getTopLineInfo } from '../api.ts'

const lineMultiData = ref([]);

const lineMultiData3 = ref([]);

const lineMultiDataHolder = ref([]);


// 日期

getTopLineInfo({}).then((res) => {
  if (res.success) {
    lineMultiData.value = [];
    res.result.forEach((item) => {
      lineMultiData.value.push({ name: item.dateTimeForHour, type: item.symbol, value: item.top10HoldAmountPercentage});
      lineMultiData3.value.push({ name: item.dateTimeForHour, type: item.symbol, value: item.top30HoldAmountPercentage});
      lineMultiDataHolder.value.push({ name: item.dateTimeForHour, type: item.symbol, value: item.totalHolderAmount});
    });
  }
});


defineProps({
  loading: {
    type: Boolean,
  },
});
const { getThemeColor } = useRootSetting();
const rankList = [];
for (let i = 0; i < 7; i++) {
  rankList.push({
    name: '白鹭岛 ' + (i + 1) + ' 号店',
    total: 1234.56 - i * 100,
  });
}

const barData = [];
for (let i = 0; i < 12; i += 1) {
  barData.push({
    name: `${i + 1}月`,
    value: Math.floor(Math.random() * 1000) + 200,
  });
}
const seriesColor = computed(() => {
  return getThemeColor.value
})
</script>

<style lang="less" scoped>
.extra-wrapper {
  line-height: 55px;
  padding-right: 24px;

  .extra-item {
    display: inline-block;
    margin-right: 24px;

    a {
      margin-left: 24px;
    }
  }
}
</style>
