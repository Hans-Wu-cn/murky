import { App } from 'vue';
import directive from './directive';
export default function (app: App<Element>) {
    app.use(directive);
}