package com.example.library.firebase;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseMultipleQueryLive extends MediatorLiveData<List<DocumentSnapshot>> {

    private List<FirebaseQueryLive> firebaseQueryList;
    private Map<Query, QuerySnapshot> querySnapshotMap;
    private Observer<QuerySnapshot> observer = new Observer<QuerySnapshot>() {
        @Override
        public void onChanged(QuerySnapshot querySnapshot) {
            querySnapshotMap.put(querySnapshot.getQuery(), querySnapshot);
            List<DocumentSnapshot> list = new ArrayList<>();
            for (QuerySnapshot snapshots : querySnapshotMap.values())
                list.addAll(snapshots.getDocuments());
            setValue(list);
        }
    };

    public FirebaseMultipleQueryLive(List<Query> queryList) {
        querySnapshotMap = new HashMap<>();
        firebaseQueryList = new ArrayList<>();
        for (Query query : queryList)
            firebaseQueryList.add(new FirebaseQueryLive(query));
    }

    @Override
    protected void onActive() {
        super.onActive();
        for (FirebaseQueryLive firebaseQuery : firebaseQueryList)
            addSource(firebaseQuery, observer);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        for (FirebaseQueryLive firebaseQuery : firebaseQueryList)
            removeSource(firebaseQuery);
    }
}