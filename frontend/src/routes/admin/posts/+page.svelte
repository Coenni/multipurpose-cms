<script>
	import { onMount } from 'svelte';
	import { postsApi } from '$lib/services/api.js';
	import Header from '$lib/components/common/Header.svelte';
	import Navigation from '$lib/components/common/Navigation.svelte';
	
	let posts = [];
	let loading = true;
	let currentPage = 0;
	let totalPages = 0;
	const siteId = 1;
	
	onMount(async () => {
		await loadPosts();
	});
	
	async function loadPosts() {
		try {
			loading = true;
			const response = await postsApi.getAll(siteId, currentPage, 10);
			posts = response.data.content;
			totalPages = response.data.totalPages;
		} catch (err) {
			console.error('Failed to load posts:', err);
		} finally {
			loading = false;
		}
	}
</script>

<div class="min-h-screen bg-gray-50">
	<Header siteName="Multipurpose CMS" />
	<Navigation />
	
	<main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
		<div class="mb-6">
			<h2 class="text-2xl font-bold text-gray-900">Posts</h2>
			<p class="text-gray-600 mt-2">Manage your blog posts</p>
		</div>
		
		{#if loading}
			<div class="flex justify-center py-12">
				<div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
			</div>
		{:else}
			<div class="bg-white shadow-sm rounded-lg p-6">
				<div class="space-y-4">
					{#each posts as post}
						<div class="border-b pb-4">
							<h3 class="text-lg font-semibold text-gray-900">{post.title}</h3>
							<p class="text-sm text-gray-600 mt-1">{post.excerpt || 'No excerpt'}</p>
							<div class="mt-2 flex items-center gap-4 text-xs text-gray-500">
								<span>Status: {post.status}</span>
								<span>Slug: {post.slug}</span>
							</div>
						</div>
					{:else}
						<div class="text-center py-12 text-gray-500">
							No posts found.
						</div>
					{/each}
				</div>
			</div>
		{/if}
	</main>
</div>
