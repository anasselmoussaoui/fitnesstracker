/*
* Copyright 2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License").
* You may not use this file except in compliance with the License.
* A copy of the License is located at
*
*  http://aws.amazon.com/apache2.0
*
* or in the "license" file accompanying this file. This file is distributed
* on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing
* permissions and limitations under the License.
*/

// NOTE: This file is generated and may not follow lint rules defined in your app
// Generated files can be excluded from analysis in analysis_options.yaml
// For more info, see: https://dart.dev/guides/language/analysis-options#excluding-code-from-analysis

// ignore_for_file: public_member_api_docs, annotate_overrides, dead_code, dead_codepublic_member_api_docs, depend_on_referenced_packages, file_names, library_private_types_in_public_api, no_leading_underscores_for_library_prefixes, no_leading_underscores_for_local_identifiers, non_constant_identifier_names, null_check_on_nullable_type_parameter, prefer_adjacent_string_concatenation, prefer_const_constructors, prefer_if_null_operators, prefer_interpolation_to_compose_strings, slash_for_doc_comments, sort_child_properties_last, unnecessary_const, unnecessary_constructor_name, unnecessary_late, unnecessary_new, unnecessary_null_aware_assignments, unnecessary_nullable_for_final_variable_declarations, unnecessary_string_interpolations, use_build_context_synchronously

import 'package:amplify_core/amplify_core.dart';
import 'package:flutter/foundation.dart';


/** This is an auto generated class representing the HeartRateModel type in your schema. */
@immutable
class HeartRateModel extends Model {
  static const classType = const _HeartRateModelModelType();
  final String id;
  final String? _data1;
  final String? _data2;
  final String? _data;
  final TemporalDateTime? _createdAt;
  final TemporalDateTime? _updatedAt;

  @override
  getInstanceType() => classType;
  
  @Deprecated('[getId] is being deprecated in favor of custom primary key feature. Use getter [modelIdentifier] to get model identifier.')
  @override
  String getId() => id;
  
  HeartRateModelModelIdentifier get modelIdentifier {
      return HeartRateModelModelIdentifier(
        id: id
      );
  }
  
  String? get data1 {
    return _data1;
  }
  
  String? get data2 {
    return _data2;
  }
  
  String? get data {
    return _data;
  }
  
  TemporalDateTime? get createdAt {
    return _createdAt;
  }
  
  TemporalDateTime? get updatedAt {
    return _updatedAt;
  }
  
  const HeartRateModel._internal({required this.id, data1, data2, data, createdAt, updatedAt}): _data1 = data1, _data2 = data2, _data = data, _createdAt = createdAt, _updatedAt = updatedAt;
  
  factory HeartRateModel({String? id, String? data1, String? data2, String? data}) {
    return HeartRateModel._internal(
      id: id == null ? UUID.getUUID() : id,
      data1: data1,
      data2: data2,
      data: data);
  }
  
  bool equals(Object other) {
    return this == other;
  }
  
  @override
  bool operator ==(Object other) {
    if (identical(other, this)) return true;
    return other is HeartRateModel &&
      id == other.id &&
      _data1 == other._data1 &&
      _data2 == other._data2 &&
      _data == other._data;
  }
  
  @override
  int get hashCode => toString().hashCode;
  
  @override
  String toString() {
    var buffer = new StringBuffer();
    
    buffer.write("HeartRateModel {");
    buffer.write("id=" + "$id" + ", ");
    buffer.write("data1=" + "$_data1" + ", ");
    buffer.write("data2=" + "$_data2" + ", ");
    buffer.write("data=" + "$_data" + ", ");
    buffer.write("createdAt=" + (_createdAt != null ? _createdAt!.format() : "null") + ", ");
    buffer.write("updatedAt=" + (_updatedAt != null ? _updatedAt!.format() : "null"));
    buffer.write("}");
    
    return buffer.toString();
  }
  
  HeartRateModel copyWith({String? data1, String? data2, String? data}) {
    return HeartRateModel._internal(
      id: id,
      data1: data1 ?? this.data1,
      data2: data2 ?? this.data2,
      data: data ?? this.data);
  }
  
  HeartRateModel.fromJson(Map<String, dynamic> json)  
    : id = json['id'],
      _data1 = json['data1'],
      _data2 = json['data2'],
      _data = json['data'],
      _createdAt = json['createdAt'] != null ? TemporalDateTime.fromString(json['createdAt']) : null,
      _updatedAt = json['updatedAt'] != null ? TemporalDateTime.fromString(json['updatedAt']) : null;
  
  Map<String, dynamic> toJson() => {
    'id': id, 'data1': _data1, 'data2': _data2, 'data': _data, 'createdAt': _createdAt?.format(), 'updatedAt': _updatedAt?.format()
  };
  
  Map<String, Object?> toMap() => {
    'id': id, 'data1': _data1, 'data2': _data2, 'data': _data, 'createdAt': _createdAt, 'updatedAt': _updatedAt
  };

  static final QueryModelIdentifier<HeartRateModelModelIdentifier> MODEL_IDENTIFIER = QueryModelIdentifier<HeartRateModelModelIdentifier>();
  static final QueryField ID = QueryField(fieldName: "id");
  static final QueryField DATA1 = QueryField(fieldName: "data1");
  static final QueryField DATA2 = QueryField(fieldName: "data2");
  static final QueryField DATA = QueryField(fieldName: "data");
  static var schema = Model.defineSchema(define: (ModelSchemaDefinition modelSchemaDefinition) {
    modelSchemaDefinition.name = "HeartRateModel";
    modelSchemaDefinition.pluralName = "HeartRateModels";
    
    modelSchemaDefinition.authRules = [
      AuthRule(
        authStrategy: AuthStrategy.PUBLIC,
        operations: [
          ModelOperation.CREATE,
          ModelOperation.UPDATE,
          ModelOperation.DELETE,
          ModelOperation.READ
        ])
    ];
    
    modelSchemaDefinition.addField(ModelFieldDefinition.id());
    
    modelSchemaDefinition.addField(ModelFieldDefinition.field(
      key: HeartRateModel.DATA1,
      isRequired: false,
      ofType: ModelFieldType(ModelFieldTypeEnum.string)
    ));
    
    modelSchemaDefinition.addField(ModelFieldDefinition.field(
      key: HeartRateModel.DATA2,
      isRequired: false,
      ofType: ModelFieldType(ModelFieldTypeEnum.string)
    ));
    
    modelSchemaDefinition.addField(ModelFieldDefinition.field(
      key: HeartRateModel.DATA,
      isRequired: false,
      ofType: ModelFieldType(ModelFieldTypeEnum.string)
    ));
    
    modelSchemaDefinition.addField(ModelFieldDefinition.nonQueryField(
      fieldName: 'createdAt',
      isRequired: false,
      isReadOnly: true,
      ofType: ModelFieldType(ModelFieldTypeEnum.dateTime)
    ));
    
    modelSchemaDefinition.addField(ModelFieldDefinition.nonQueryField(
      fieldName: 'updatedAt',
      isRequired: false,
      isReadOnly: true,
      ofType: ModelFieldType(ModelFieldTypeEnum.dateTime)
    ));
  });
}

class _HeartRateModelModelType extends ModelType<HeartRateModel> {
  const _HeartRateModelModelType();
  
  @override
  HeartRateModel fromJson(Map<String, dynamic> jsonData) {
    return HeartRateModel.fromJson(jsonData);
  }
  
  @override
  String modelName() {
    return 'HeartRateModel';
  }
}

/**
 * This is an auto generated class representing the model identifier
 * of [HeartRateModel] in your schema.
 */
@immutable
class HeartRateModelModelIdentifier implements ModelIdentifier<HeartRateModel> {
  final String id;

  /** Create an instance of HeartRateModelModelIdentifier using [id] the primary key. */
  const HeartRateModelModelIdentifier({
    required this.id});
  
  @override
  Map<String, dynamic> serializeAsMap() => (<String, dynamic>{
    'id': id
  });
  
  @override
  List<Map<String, dynamic>> serializeAsList() => serializeAsMap()
    .entries
    .map((entry) => (<String, dynamic>{ entry.key: entry.value }))
    .toList();
  
  @override
  String serializeAsString() => serializeAsMap().values.join('#');
  
  @override
  String toString() => 'HeartRateModelModelIdentifier(id: $id)';
  
  @override
  bool operator ==(Object other) {
    if (identical(this, other)) {
      return true;
    }
    
    return other is HeartRateModelModelIdentifier &&
      id == other.id;
  }
  
  @override
  int get hashCode =>
    id.hashCode;
}