<script>
	import { onMount } from 'svelte';
	import { postsApi } from '../../../frontend/src/lib/services/api.js';
	
	export let settings = {
		title: 'Recent Posts',
		numberOfPosts: 5,
		showExcerpt: false,
		showDate: true
	};
	
	let posts = [];
	let loading = true;
	
	onMount(async () => {
		try {
			const response = await postsApi.getAll(1, 0, settings.numberOfPosts);
			posts = response.data.content || [];
		} catch (err) {
			console.error('Failed to load recent posts:', err);
		} finally {
			loading = false;
		}
	});
	
	function formatDate(dateString) {
		if (!dateString) return '';
		const date = new Date(dateString);
		return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
	}
</script>

<div class="widget recent-posts-widget">
	<h3 class="widget-title">{settings.title}</h3>
	{#if loading}
		<div class="text-sm text-gray-500">Loading...</div>
	{:else if posts.length > 0}
		<ul class="space-y-3">
			{#each posts as post}
				<li class="border-b pb-2 last:border-b-0">
					<a href="/posts/{post.slug}" class="text-primary hover:underline font-medium">
						{post.title}
					</a>
					{#if settings.showDate && post.publishDate}
						<div class="text-xs text-gray-500 mt-1">
							{formatDate(post.publishDate)}
						</div>
					{/if}
					{#if settings.showExcerpt && post.excerpt}
						<p class="text-sm text-gray-600 mt-1">
							{post.excerpt.substring(0, 100)}...
						</p>
					{/if}
				</li>
			{/each}
		</ul>
	{:else}
		<p class="text-sm text-gray-500">No recent posts found.</p>
	{/if}
</div>
