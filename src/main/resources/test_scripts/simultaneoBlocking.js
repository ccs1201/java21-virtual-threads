import {Httpx} from 'https://jslib.k6.io/httpx/0.1.0/index.js';

const session = new Httpx({
    baseURL: 'http://localhost:8080/api/blocking',
    timeout: 240000,
});
export const options = {
    scenarios: {
        all: {
            executor: 'per-vu-iterations',
            vus: 500,
            iterations: 4,
        },
    },

};
export default async function test() {
    Promise.allSettled([session.asyncGet('/virtual'), session.asyncGet('/forkjoin')]);
    // await session.asyncGet('/platform');
}
