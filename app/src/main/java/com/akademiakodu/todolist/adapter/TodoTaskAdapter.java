package com.akademiakodu.todolist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.akademiakodu.todolist.R;
import com.akademiakodu.todolist.model.TodoTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class TodoTaskAdapter extends RecyclerView.Adapter<TodoTaskAdapter.TodoViewHolder> {
    private List<TodoTask> mTasks;
    private OnClickListener mClickListener;

    public TodoTaskAdapter(List<TodoTask> tasks, OnClickListener clickListener) {
        mTasks = tasks;
        mClickListener = clickListener;
    }

    public void setTasks(List<TodoTask> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.list_item_todo, parent, false);
        return new TodoViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        // Pobranie elementu danych na pozycji position
        TodoTask task = mTasks.get(position);
        // Uzupełenienie widoku (holder) na podstawie pobranego obiektu
        holder.mBlockListeners = true; // Blokujemy wysylaine powiadomien o zmianie checkboxa i kliknieciu
        holder.mCurrentPosition = task.getId();
        holder.mCurrentTask = task;
        holder.mTitle.setText(task.getName());
        holder.mDone.setChecked(task.isDone());
        holder.mBlockListeners = false; // Odblokowujemy powiadomienia, zeby poprawnie
        // reagowac na zdarzenia uzytkownikow
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.task_done)
        CheckBox mDone;
        @BindView(R.id.task_title)
        TextView mTitle;

        TodoTask mCurrentTask;
        int mCurrentPosition;
        // True - poniewaz na poczatku kiedy powstaje wiersz i jest przed przypisaniem pierwszego zadania
        // nie chcemy zeby uruchamialy sie jakiekolwiek funkcje dotyczace zdarzen (np. onClick, onChecked)
        boolean mBlockListeners = true;

        public TodoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void onItemClick() {
            if (mClickListener != null && !mBlockListeners) {
                mClickListener.onClick(mCurrentTask, mCurrentPosition);
            }
        }

        @OnCheckedChanged(R.id.task_done)
        void onCheckedChange(boolean checked) {
            if (mClickListener != null && !mBlockListeners) {
                mClickListener.onTaskDoneChanged(mCurrentTask, mCurrentPosition, checked);
            }
        }
    }

    public interface OnClickListener {
        void onClick(TodoTask task, int position);
        void onTaskDoneChanged(TodoTask task, int position, boolean isDone);
    }
}








