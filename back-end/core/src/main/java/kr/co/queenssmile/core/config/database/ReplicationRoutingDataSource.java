//package kr.co.queenssmile.core.config.database;
//
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//import org.springframework.transaction.support.TransactionSynchronizationManager;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {
//
//  private CircularList<String> dataSourceNameList;
//
//  @Override
//  public void setTargetDataSources(Map<Object, Object> targetDataSources) {
//    super.setTargetDataSources(targetDataSources);
//
//    dataSourceNameList = new CircularList<>(
//        targetDataSources.keySet()
//            .stream()
//            .filter(key -> key.toString().contains("slave"))
//            .map(key -> key.toString())
//            .collect(Collectors.toList())
//    );
//  }
//
//  @Override
//  protected Object determineCurrentLookupKey() {
//    boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
//    if(isReadOnly) {
//      return dataSourceNameList.getOne();
//    } else {
//      return "master";
//    }
//  }
//
//  static class CircularList <T> implements Serializable {
//
//    private List<T> list;
//    private Integer counter = 0;
//
//    public CircularList(List<T> list) {
//      this.list = list;
//    }
//    public T getOne() {
//      if(counter + 1 >= list.size()) {
//        counter = -1;
//      }
//      return list.get(++counter);
//    }
//  }
//}