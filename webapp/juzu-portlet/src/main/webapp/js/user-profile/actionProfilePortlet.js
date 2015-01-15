(function ($) {
  var ActionProfile = {
      parentId : '#ActionProfilePortlet',
      init : function() {
        var portlet = $(ActionProfile.parentId);
        portlet.find('button.btn').on('click', ActionProfile.doAction);
        //
        ActionProfile.leftBorder();
      },
      doAction : function() {
        var action = $(this).data('action');
        if(action && action.length > 0) {
          $(ActionProfile.parentId).jzAjax({
            url : "RelationshipAction.doAction()",
            data : {
              "actionName" : action
            },
            success : function(data) {
              if(data.ok === 'true') {
                var content = $('<div></div>').html(data.content);
                $(ActionProfile.parentId).replaceWith(content.find(ActionProfile.parentId));
                //
                ActionProfile.init();
              } else {
                window.console.error(data.message);
              }
            }
          }).fail(function(jqXHR, textStatus) {
            window.console.error( "Request failed: " + textStatus + " " + jqXHR);
          });
        }
      },
      leftBorder : function() {
        var leftRow = $('.left-column-containerTDContainer:first');
        if(leftRow.length > 0) {
          leftRow.css('position', 'relative');
          leftRow.append($('<div class="left-border-row"></div>'))
        }
      }
  }
  ActionProfile.init();
  return ActionProfile;
})(jq);