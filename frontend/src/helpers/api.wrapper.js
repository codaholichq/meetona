import { useAuthStore } from '@/stores';

export const apiWrapper = {
    get: request('GET'),
    post: request('POST'),
    put: request('PUT'),
    delete: request('DELETE')
};

function request(method) {
    return (url, body) => {
        const requestOptions = {
            method,
            headers: authHeader(url)
        };
        if (body) {
            requestOptions.headers['Content-Type'] = 'application/json';
            requestOptions.body = JSON.stringify(body);
        }
        return fetch(url, requestOptions).then(handleResponse);
    }
}

/** Helper Functions **/
function authHeader(url) {
    const { user } = useAuthStore();
    const token = user?.data.accessToken;
    const isApiUrl = url.startsWith(import.meta.env.VITE_API_URL);
    if (!!token && isApiUrl) {
        return { Authorization: `Bearer ${token}` }
    }
    return {};
}

function handleResponse(response) {
    return response.text().then(text => {
        const data = text && JSON.parse(text);

        if (!response.ok) {
            const { user, logout } = useAuthStore();
            if ([401, 403].includes(response.status) && user) {
                logout();
            }
            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }
        return data;
    });
}
