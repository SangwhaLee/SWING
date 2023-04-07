export const API_URL = 'https://j8a405.p.ssafy.io/api';
export const AI_API_URL = 'https://j8a405.p.ssafy.io/apid';

export const BasicProfile =
  'https://a405-swing.s3.ap-northeast-2.amazonaws.com/images/profile/default.png';

export function setCookie(key, value, expiredays) {
  let todayDate = new Date();
  todayDate.setDate(todayDate.getDate() + expiredays); // 현재 시각 + 일 단위로 쿠키 만료 날짜 변경
  //todayDate.setTime(todayDate.getTime() + (expiredays * 24 * 60 * 60 * 1000)); // 밀리세컨드 단위로 쿠키 만료 날짜 변경
  document.cookie =
    key + '=' + value + ';expires=' + todayDate.toUTCString() + ';path=/';
}

export function getCookie(key) {
  let value = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');
  return value ? value[2] : null;
}

export function delCookie(key) {
  let todayDate = new Date();
  document.cookie = key + '=; path=/; expires=' + todayDate.toGMTString() + ';'; // 현재 시각 이전이면 쿠키가 만료되어 사라짐.
}
