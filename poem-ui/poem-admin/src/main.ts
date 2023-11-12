/* eslint-disable simple-import-sort/imports */
import TDesign from 'tdesign-vue-next';
import { createApp } from 'vue';

import App from './App.vue';
import router from './router';
import { store } from './store';
import plugins from './plugins/index'

import 'tdesign-vue-next/es/style/index.css';
import '@/style/index.less';
import './permission';
import i18n, { initLanguage } from './i18n';

const app = createApp(App);

app.use(TDesign);
app.use(store);
app.use(router);
app.use(plugins)
app.use(i18n)

app.mount('#app');
await initLanguage()