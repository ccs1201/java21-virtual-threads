import {Httpx} from 'https://jslib.k6.io/httpx/0.1.0/index.js';

const session = new Httpx({
    baseURL: 'http://localhost:8080/api/test',
    timeout: 180000,
});
export const options = {
    scenarios: {
        all: {
            executor: 'per-vu-iterations',
            vus: 500,
            iterations: 2,
        },
    },
};
export default async function test() {
    Promise.allSettled([session.asyncGet('/forkjoin'), session.asyncGet('/virtual')]);
    // await session.asyncGet('/platform');
    // await session.asyncGet('/virtual');
}
