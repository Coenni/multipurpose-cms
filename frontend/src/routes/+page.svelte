<script>
	import Header from '$lib/components/common/Header.svelte';
	import Navigation from '$lib/components/common/Navigation.svelte';
	import Footer from '$lib/components/common/Footer.svelte';
</script>

<div class="min-h-screen flex flex-col">
	<Header siteName="Multipurpose CMS" />
	<Navigation />
	
	<main class="flex-grow">
		<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
			<div class="text-center">
				<h1 class="text-4xl font-bold text-gray-900 mb-4">
					Welcome to Multipurpose CMS
				</h1>
				<p class="text-xl text-gray-600 mb-8">
					A powerful, flexible content management system built with Spring Boot and SvelteKit
				</p>
				
				<div class="grid grid-cols-1 md:grid-cols-3 gap-8 mt-12">
					<div class="bg-white p-6 rounded-lg shadow-md">
						<div class="text-primary text-4xl mb-4">🎨</div>
						<h2 class="text-xl font-semibold mb-2">Themes</h2>
						<p class="text-gray-600 mb-4">
							Customizable themes to match your brand and style
						</p>
						<a href="/admin/themes" class="text-primary hover:underline">
							Manage Themes →
						</a>
					</div>
					
					<div class="bg-white p-6 rounded-lg shadow-md">
						<div class="text-primary text-4xl mb-4">🧩</div>
						<h2 class="text-xl font-semibold mb-2">Plugins & Widgets</h2>
						<p class="text-gray-600 mb-4">
							Extend functionality with powerful plugins and widgets
						</p>
						<a href="/admin/plugins" class="text-primary hover:underline">
							Manage Plugins →
						</a>
					</div>
					
					<div class="bg-white p-6 rounded-lg shadow-md">
						<div class="text-primary text-4xl mb-4">📝</div>
						<h2 class="text-xl font-semibold mb-2">Content Management</h2>
						<p class="text-gray-600 mb-4">
							Create and manage pages, posts, and products with ease
						</p>
						<a href="/admin/pages" class="text-primary hover:underline">
							Manage Content →
						</a>
					</div>
				</div>
				
				<div class="mt-12 bg-primary-50 p-8 rounded-lg">
					<h2 class="text-2xl font-bold text-gray-900 mb-4">Key Features</h2>
					<div class="grid grid-cols-1 md:grid-cols-2 gap-4 text-left">
						<div class="flex items-start">
							<span class="text-primary mr-2">✓</span>
							<span>Multi-tenant site support</span>
						</div>
						<div class="flex items-start">
							<span class="text-primary mr-2">✓</span>
							<span>Theme system with import/export</span>
						</div>
						<div class="flex items-start">
							<span class="text-primary mr-2">✓</span>
							<span>Plugin/Widget system</span>
						</div>
						<div class="flex items-start">
							<span class="text-primary mr-2">✓</span>
							<span>SEO optimization</span>
						</div>
						<div class="flex items-start">
							<span class="text-primary mr-2">✓</span>
							<span>Media library management</span>
						</div>
						<div class="flex items-start">
							<span class="text-primary mr-2">✓</span>
							<span>E-commerce support</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>
	
	<Footer siteName="Multipurpose CMS" />
</div>
