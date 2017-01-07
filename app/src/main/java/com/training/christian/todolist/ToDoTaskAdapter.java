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
import butterknife.OnCheckedChanged;
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
        holder.mBlockListeners = true;// Blokujemy wysylanie powIADOMIEN O ZMIANIE CHECKBOXA i click
        holder.mCurrentTask = task;
        holder.mCurrentPosition = task.getId();
        holder.mTilte.setText(task.getName());
        holder.mDone.setChecked(task.isDone());
        holder.mBlockListeners = false;
        // odblokowujemy powiadomienia, zeby poprawnie reagowac na zdarzenia uzytkownika
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
        //True ponieważ na początku kiedy powstaje wiersz i jest przed przypisanie ierwszego zadania
        //nie chcemy zeby uruchamialy sie jakiekolwiek funkcje dotyczace Onclick cy onChecked
        boolean mBlockListeners = true;

        public ToDoViewHolder(View itemView) {
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
        void onClick(ToDoTask task, int position);

        void onTaskDoneChanged(ToDoTask task, int position, boolean isDone);
    }
}
