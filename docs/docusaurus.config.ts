import {themes as prismThemes} from 'prism-react-renderer';
import type {Config} from '@docusaurus/types';
import type * as Preset from '@docusaurus/preset-classic';

// This runs in Node.js - Don't use client-side code here (browser APIs, JSX...)

const config: Config = {
  title: 'Spring Boot Training',
  tagline: 'A website for learning Spring Boot',
  favicon: 'img/spring.png',

  // Set the production url of your site here
  url: 'https://jdarguello.github.io/',
  // Set the /<baseUrl>/ pathname under which your site is served
  // For GitHub pages deployment, it is often '/<projectName>/'
  baseUrl: '/Spring/',

  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  organizationName: 'jdarguello', // Usually your GitHub org/user name.
  projectName: 'Spring', // Usually your repo name.
  deploymentBranch: 'main',

  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',

  // Even if you don't use internationalization, you can use this field to set
  // useful metadata like html lang. For example, if your site is Chinese, you
  // may want to replace "en" with "zh-Hans".
  i18n: {
    defaultLocale: 'en',
    locales: ['en'],
  },

  presets: [
    [
      'classic',
      {
        docs: {
          sidebarPath: './sidebars.ts',
          exclude: ['**/relaciones/**'],
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
            'https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/',
        },
        blog: {
          showReadingTime: true,
          feedOptions: {
            type: ['rss', 'atom'],
            xslt: true,
          },
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
            'https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/',
          // Useful options to enforce blogging best practices
          onInlineTags: 'warn',
          onInlineAuthors: 'warn',
          onUntruncatedBlogPosts: 'warn',
        },
        theme: {
          customCss: './src/css/custom.css',
        },
      } satisfies Preset.Options,
    ],
  ],

  themeConfig: {
    // Replace with your project's social card
    image: 'img/docusaurus-social-card.jpg',
    navbar: {
      title: 'Spring Boot',
      logo: {
        alt: 'My Site Logo',
        src: 'img/spring.png',
      },
      items: [
        {
          type: 'docSidebar',
          sidebarId: 'introSidebar',
          position: 'left',
          label: '1. Introducción',
        },
        {
          type: 'docSidebar',
          sidebarId: 'dataSidebar',
          position: 'left',
          label: '2. Spring Data',
        },
        {
          type: 'docSidebar',
          sidebarId: 'testSidebar',
          position: 'left',
          label: '3. Testing',
        },
        {
          type: 'docSidebar',
          sidebarId: 'secSidebar',
          position: 'left',
          label: '4. Spring Security',
        },
        {
          type: 'docSidebar',
          sidebarId: 'funcReactSidebar',
          position: 'left',
          label: '5. Concurrencia y Paralelismo',
        },
        {
          type: 'docSidebar',
          sidebarId: 'monitoreoSidebar',
          position: 'left',
          label: '6. Monitoreo',
        },
      ],
    },
    footer: {
      style: 'dark',
      links: [
        {
          title: 'Docs',
          items: [
            {
              label: 'Tutorial',
              to: '/docs/intro',
            },
          ],
        },
        {
          title: 'Community',
          items: [
            {
              label: 'Stack Overflow',
              href: 'https://stackoverflow.com/questions/tagged/docusaurus',
            },
            {
              label: 'Discord',
              href: 'https://discordapp.com/invite/docusaurus',
            },
            {
              label: 'X',
              href: 'https://x.com/docusaurus',
            },
          ],
        },
        {
          title: 'More',
          items: [
            {
              label: 'Blog',
              to: '/blog',
            },
            {
              label: 'GitHub',
              href: 'https://github.com/facebook/docusaurus',
            },
          ],
        },
      ],
      copyright: `Copyright © ${new Date().getFullYear()} My Project, Inc. Built with Docusaurus.`,
    },
    prism: {
      theme: prismThemes.github,
      darkTheme: prismThemes.dracula,
      additionalLanguages: ['java'],
    },
  } satisfies Preset.ThemeConfig,
};

export default config;
