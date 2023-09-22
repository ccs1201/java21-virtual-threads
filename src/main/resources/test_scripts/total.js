import {Httpx} from 'https://jslib.k6.io/httpx/0.1.0/index.js';

const session = new Httpx({
    baseURL: 'http://localhost:8080/api/test',
    timeout: 60000,
});

export default async function test() {
    await session.asyncGet('/total');
    await session.asyncGet('/totalvirtual');
}
