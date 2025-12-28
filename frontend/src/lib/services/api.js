import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

const apiClient = axios.create({
	baseURL: `${API_BASE_URL}/api`,
	headers: {
		'Content-Type': 'application/json'
	}
});

// Themes API
export const themesApi = {
	getAll: () => apiClient.get('/themes'),
	getBySlug: (slug) => apiClient.get(`/themes/${slug}`),
	getInstalled: () => apiClient.get('/themes/installed'),
	getActive: () => apiClient.get('/themes/active'),
	create: (theme) => apiClient.post('/themes', theme),
	update: (slug, theme) => apiClient.put(`/themes/${slug}`, theme),
	install: (slug) => apiClient.post(`/themes/${slug}/install`),
	activate: (slug) => apiClient.put(`/themes/${slug}/activate`),
	delete: (slug) => apiClient.delete(`/themes/${slug}`),
	upload: (file) => {
		const formData = new FormData();
		formData.append('file', file);
		return apiClient.post('/themes/upload', formData, {
			headers: { 'Content-Type': 'multipart/form-data' }
		});
	}
};

// Plugins API
export const pluginsApi = {
	getAll: () => apiClient.get('/plugins'),
	getBySlug: (slug) => apiClient.get(`/plugins/${slug}`),
	getActive: () => apiClient.get('/plugins/active'),
	getByCategory: (category) => apiClient.get(`/plugins/category/${category}`),
	create: (plugin) => apiClient.post('/plugins', plugin),
	update: (slug, plugin) => apiClient.put(`/plugins/${slug}`, plugin),
	install: (slug) => apiClient.post(`/plugins/${slug}/install`),
	toggleActivation: (slug) => apiClient.put(`/plugins/${slug}/activate`),
	delete: (slug) => apiClient.delete(`/plugins/${slug}`)
};

// Pages API
export const pagesApi = {
	getAll: (siteId) => apiClient.get('/pages', { params: { siteId } }),
	getPublished: (siteId) => apiClient.get('/pages/published', { params: { siteId } }),
	getById: (id) => apiClient.get(`/pages/${id}`),
	getBySlug: (slug, siteId) => apiClient.get(`/pages/slug/${slug}`, { params: { siteId } }),
	create: (page) => apiClient.post('/pages', page),
	update: (id, page) => apiClient.put(`/pages/${id}`, page),
	delete: (id) => apiClient.delete(`/pages/${id}`)
};

// Posts API
export const postsApi = {
	getAll: (siteId, page = 0, size = 10) => 
		apiClient.get('/posts', { params: { siteId, page, size } }),
	getByStatus: (status, siteId, page = 0, size = 10) => 
		apiClient.get(`/posts/status/${status}`, { params: { siteId, page, size } }),
	getById: (id) => apiClient.get(`/posts/${id}`),
	getBySlug: (slug, siteId) => apiClient.get(`/posts/slug/${slug}`, { params: { siteId } }),
	create: (post) => apiClient.post('/posts', post),
	update: (id, post) => apiClient.put(`/posts/${id}`, post),
	delete: (id) => apiClient.delete(`/posts/${id}`)
};

// Products API
export const productsApi = {
	getAll: (siteId, page = 0, size = 10) => 
		apiClient.get('/products', { params: { siteId, page, size } }),
	getPublished: (siteId, page = 0, size = 10) => 
		apiClient.get('/products/published', { params: { siteId, page, size } }),
	getById: (id) => apiClient.get(`/products/${id}`),
	create: (product) => apiClient.post('/products', product),
	update: (id, product) => apiClient.put(`/products/${id}`, product),
	delete: (id) => apiClient.delete(`/products/${id}`)
};

// Media API
export const mediaApi = {
	getAll: (siteId, page = 0, size = 20) => 
		apiClient.get('/media', { params: { siteId, page, size } }),
	getById: (id) => apiClient.get(`/media/${id}`),
	upload: (siteId, file, uploadedBy = null) => {
		const formData = new FormData();
		formData.append('file', file);
		return apiClient.post('/media/upload', formData, {
			params: { siteId, uploadedBy },
			headers: { 'Content-Type': 'multipart/form-data' }
		});
	},
	delete: (id) => apiClient.delete(`/media/${id}`)
};

// Widgets API
export const widgetsApi = {
	getAll: (configId) => apiClient.get('/widgets/instances', { params: { configId } }),
	getByArea: (areaId, configId) => apiClient.get(`/widgets/areas/${areaId}`, { params: { configId } }),
	create: (widget) => apiClient.post('/widgets/instances', widget),
	update: (id, widget) => apiClient.put(`/widgets/instances/${id}`, widget),
	delete: (id) => apiClient.delete(`/widgets/instances/${id}`)
};

export default apiClient;
