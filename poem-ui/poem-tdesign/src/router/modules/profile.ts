import Layout from '@/layouts/index.vue';
export default [
  {
    path: '/profile',
    name: 'profile',
    redirect: '/profile/index',
    component: Layout,
    meta: { title: '个人中心', icon: 'user-circle', hidden: false },
    children: [
      {
        path: 'index',
        name: 'Profile',
        component: () => import('@/pages/profile/index.vue'),
        meta: {
          title: '个人中心',
        },
      },
    ]
  },
];
