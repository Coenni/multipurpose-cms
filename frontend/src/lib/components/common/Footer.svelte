<script>
	const currentYear = new Date().getFullYear();
	export let siteName = 'CMS';
</script>

<footer class="bg-gray-800 text-white">
	<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
		<div class="grid grid-cols-1 md:grid-cols-3 gap-8">
			<div>
				<h3 class="text-lg font-semibold mb-4">{siteName}</h3>
				<p class="text-gray-400 text-sm">
					A multipurpose CMS built with Spring Boot and SvelteKit
				</p>
			</div>
			<div>
				<h3 class="text-lg font-semibold mb-4">Quick Links</h3>
				<ul class="space-y-2">
					<li><a href="/" class="text-gray-400 hover:text-white transition-colors">Home</a></li>
					<li><a href="/admin/themes" class="text-gray-400 hover:text-white transition-colors">Admin</a></li>
				</ul>
			</div>
			<div>
				<h3 class="text-lg font-semibold mb-4">Resources</h3>
				<ul class="space-y-2">
					<li><a href="#" class="text-gray-400 hover:text-white transition-colors">Documentation</a></li>
					<li><a href="#" class="text-gray-400 hover:text-white transition-colors">Support</a></li>
				</ul>
			</div>
		</div>
		<div class="border-t border-gray-700 mt-8 pt-8 text-center text-gray-400 text-sm">
			<p>&copy; {currentYear} {siteName}. All rights reserved.</p>
		</div>
	</div>
</footer>
