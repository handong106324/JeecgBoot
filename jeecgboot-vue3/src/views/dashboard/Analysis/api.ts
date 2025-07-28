import { defHttp } from '/@/utils/http/axios';

enum Api {
  loginfo = '/sys/loginfo',
  visitInfo = '/sys/visitInfo',
  topLineInfo = '/demo/chart/topInfo',
  totalInfo = '/demo/chart/total'
}
/**
 * 日志统计信息
 * @param params
 */
export const getLoginfo = (params) => defHttp.get({ url: Api.loginfo, params }, { isTransformResponse: false });
/**
 * 访问量信息
 * @param params
 */
export const getVisitInfo = (params) => defHttp.get({ url: Api.visitInfo, params }, { isTransformResponse: false });


export const getTopLineInfo = (params) => defHttp.get({ url: Api.topLineInfo, params }, { isTransformResponse: false });

export const getTotalInfo = () => defHttp.get({ url: Api.totalInfo }, { isTransformResponse: false });
