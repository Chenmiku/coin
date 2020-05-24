<fieldset>
  <div class="row">
    <div class="col-lg-6">
      <div class="form-group">
        <label class="col-xs-2 control-label">Ngày đăng ký</label>

        <div class="col-xs-5 ">
          <div class="date list-filter-date date-wave" data-type="startDate">
            <input name="startDate" type="text" class="form-control" placeholder="Start" value="${startDate!}"
                   autocomplete="off">
          </div>
        </div>
        <div class="col-xs-5">
          <div class="date list-filter-date" data-type="endDate">
            <input name="endDate" type="text" class="form-control" placeholder="End" value="${endDate!}"
                   autocomplete="off">
          </div>
        </div>
      </div>
    </div>
  </div>
</fieldset>