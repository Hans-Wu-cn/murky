import Layout from '@/layouts/index.vue';
export default [
  {
    path: '/profile',
    name: 'profile',
    redirect: '/profile/index',
    component: Layout,
    meta: { title: 'menu:PersonalCenter', icon: 'user-circle', hidden: true },
    children: [
      {
        path: 'index',
        name: 'Profile',
        component: () => import('@/pages/profile/index.vue'),
        meta: {
          title: 'menu:PersonalCenter',
        },
      },
    ]
  },
];
