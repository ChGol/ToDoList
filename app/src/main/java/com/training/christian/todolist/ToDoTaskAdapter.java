package com.training.christian.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToDoTaskAdapter extends RecyclerView.Adapter<ToDoTaskAdapter.ToDoViewHolder> {

    private List<ToDoTask> mTasks;
    private OnClickListener mClickListener;

    public ToDoTaskAdapter(List<ToDoTask> mTasks, OnClickListener mClickListener) {
        this.mTasks = mTasks;
        this.mClickListener = mClickListener;
    }

    public void setTasks(List<ToDoTask> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.list_item_todo, parent, false);
        return new ToDoViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        //1 Pobranie elementu danyh na poycji position
        ToDoTask task = mTasks.get(position);
        //2 uzupełnienie widoku holdera na podstawie pobraengo obiektu
        holder.mTilte.setText(task.getName());
        holder.mDone.setChecked(task.isDone());
        holder.mCurrentTask = task;
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.task_done)
        CheckBox mDone;
        @BindView(R.id.task_title)
        TextView mTilte;

        ToDoTask mCurrentTask;
        int mCurrentPosition;

        public ToDoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void onItemClick(){
            if (mClickListener!=null){
                mClickListener.onClick(mCurrentTask, mCurrentPosition);
            }
        }
    }

    public interface OnClickListener {
        void onClick(ToDoTask task, int position);
    }
}
