export function getIconNames() {
  // @/assets/icons/*.svg
  const files = import.meta.globEager('@/assets/icons/*.svg'); //传入要拿到文件名字的文件夹path

  const re = /\/icons\/(.*)\.svg/; //定义拿到文件名的正则表达式

  let arr:Array<string> = []

  Object.keys(files).forEach((item) => {
    arr.push(item.match(re)[1]);
  });

  return arr;
}
