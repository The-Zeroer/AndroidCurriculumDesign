package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.organization;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织架构Fragment
 * 展示领导→部门→成员的树形结构，支持展开/收起
 */
public class OrganizationFragment extends BaseFragment<BaseViewModel> {

    private ExpandableListView expandableListView;
    private List<String> groupList;      // 一级：部门/领导名称
    private List<List<String>> childList; // 二级：成员列表

    /**
     * 返回0表示不使用XML布局
     */
    @Override
    protected int getLayoutId() {
        return 0;
    }

    /**
     * 完全重写onCreateView，自己创建布局
     */
    @Override
    public View onCreateView(android.view.LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 1. 创建根布局
        LinearLayout rootLayout = new LinearLayout(getContext());
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(Color.WHITE);

        // 2. 创建标题栏
        TextView titleView = new TextView(getContext());
        titleView.setText("组织架构");
        titleView.setTextSize(20);
        titleView.setPadding(40, 50, 40, 30);
        titleView.setTextColor(Color.BLACK);
        titleView.setBackgroundColor(Color.parseColor("#EEEEEE"));
        rootLayout.addView(titleView);

        // 3. 创建可展开列表控件
        expandableListView = new ExpandableListView(getContext());
        expandableListView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        rootLayout.addView(expandableListView);

        // 4. 初始化数据
        initOrgData();

        // 5. 设置适配器
        ExpandableListAdapter adapter = new ExpandableListAdapter();
        expandableListView.setAdapter(adapter);

        // 6. 默认展开所有分组
        for (int i = 0; i < groupList.size(); i++) {
            expandableListView.expandGroup(i);
        }

        // 将rootView设置为我们的布局
        this.rootView = rootLayout;
        return rootLayout;
    }

    @Override
    protected void onInitView(View rootView) {
        // 已经在onCreateView完成UI初始化
    }

    @Override
    protected void onInitHandler() {
        // 如有点击事件可在此添加
    }

    /**
     * 初始化组织架构数据
     */
    private void initOrgData() {
        groupList = new ArrayList<>();
        childList = new ArrayList<>();

        // 管理层
        groupList.add("  管理层");
        List<String> leaderMembers = new ArrayList<>();
        leaderMembers.add("   • 总经理");
        leaderMembers.add("   • 副总经理");
        leaderMembers.add("   • 技术总监");
        childList.add(leaderMembers);

        // 技术开发部
        groupList.add("  技术开发部");
        List<String> techMembers = new ArrayList<>();
        techMembers.add("   • 安卓开发工程师 - 张三");
        techMembers.add("   • 后端开发工程师 - 李四");
        techMembers.add("   • 前端开发工程师 - 王五");
        techMembers.add("   • 测试工程师 - 赵六");
        techMembers.add("   • 数据库工程师 - 钱七");
        childList.add(techMembers);

        // 产品设计部
        groupList.add("  产品设计部");
        List<String> productMembers = new ArrayList<>();
        productMembers.add("   • 产品经理 - 周八");
        productMembers.add("   • UI设计师 - 吴九");
        productMembers.add("   • UX设计师 - 郑十");
        productMembers.add("   • 交互设计师 - 陈甲");
        childList.add(productMembers);

        // 运营推广部
        groupList.add("  运营推广部");
        List<String> operateMembers = new ArrayList<>();
        operateMembers.add("   • 运营主管 - 林乙");
        operateMembers.add("   • 内容运营 - 黄丙");
        operateMembers.add("   • 用户运营 - 许丁");
        operateMembers.add("   • 新媒体运营 - 何戊");
        childList.add(operateMembers);

        // 客户服务部
        groupList.add("  客户服务部");
        List<String> serviceMembers = new ArrayList<>();
        serviceMembers.add("   • 客服主管 - 宋己");
        serviceMembers.add("   • 在线客服 - 苏庚");
        serviceMembers.add("   • 投诉处理 - 卢辛");
        childList.add(serviceMembers);
    }

    /**
     * 自定义适配器：处理可展开列表的显示（无三角图标版本）
     */
    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return groupList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        /**
         * 一级分组视图（部门/领导）- 无三角图标
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            TextView groupTv = new TextView(getContext());
            String groupName = groupList.get(groupPosition);

            // 直接显示部门名称，不加三角符号
            groupTv.setText(groupName+" ");
            groupTv.setTextSize(18);
            groupTv.setTextColor(Color.BLACK);
            groupTv.setPadding(50, 45, 40, 45);
            groupTv.setBackgroundColor(Color.parseColor("#F5F5F5"));

            return groupTv;
        }

        /**
         * 二级成员视图
         */
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            TextView childTv = new TextView(getContext());
            String childName = childList.get(groupPosition).get(childPosition);

            childTv.setText(childName);
            childTv.setTextSize(15);
            childTv.setTextColor(Color.DKGRAY);
            childTv.setPadding(90, 35, 40, 35);

            // 添加分割线效果
            if (!isLastChild) {
                childTv.setBackgroundColor(Color.parseColor("#FAFAFA"));
            }

            return childTv;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}