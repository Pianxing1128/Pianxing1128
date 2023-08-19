package com.qc.domain.course;

public enum CourseTypeEnum {
    FreeCourse(0),
    MembershipCourse(1),
    PurchaseRequiredCourse(2);

    private final int value;

    CourseTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CourseTypeEnum fromValue(int value) {
        for (CourseTypeEnum type : CourseTypeEnum.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid CourseType value: " + value);
    }
}
