# Assignment 1 - Onboarding Screens

تطبيق Android متعدد الخطوات (Multi-step Onboarding) باستخدام **Fragments**، مع تمرير البيانات بين الـ Fragments والـ Host Activity عبر **Interfaces**، وتغيير ديناميكي لواجهة المستخدم.

## خطوات التطبيق

1. **Fragment1** - شاشة ترحيب مع زر `Get Started`.
2. **Fragment2** - نموذج لجمع بيانات المستخدم:
   - الاسم (إلزامي)
   - البريد الإلكتروني (اختياري + تحقق من الصيغة إذا تم إدخاله)
   - الجنس (RadioGroup)
   - العمر (اختياري)
   - الجامعة (اختيارية)
3. **Fragment3** - شاشة المراجعة:
   - تعرض اسم المستخدم الذي وصل من الـ Host Activity.
   - تحتوي CheckBox: `I confirm I have read and agree to use the app.`
   - زر `Continue` يكون **معطلاً** في البداية.
   - عند تحديد الـ CheckBox: يتفعل الزر فوراً ويتحول نصه إلى `Finish`.
   - عند إلغاء التحديد: يتعطل الزر فوراً ويعود نصه إلى `Continue`.

## كيف تم تحقيق متطلبات الواجب؟

| المتطلب | مكان التنفيذ |
|---|---|
| إضافة حقول أكثر في Fragment2 | `fragment_2.xml` + `Fragment2.java` |
| إرسال الاسم إلى الـ Host عبر Interface | `Fragment2.OnFragment2Listener` |
| الـ Host ينتقل إلى Fragment3 | `MainActivity.onFragment2Continue(...)` |
| تمرير الاسم إلى Fragment3 باستخدام Bundle | `Fragment3.newInstance(name)` + `setArguments(...)` |
| عرض الاسم في Fragment3 | `Fragment3.onViewCreated(...)` |
| CheckBox + زر معطل في البداية | `fragment_3.xml` |
| إدارة حالة الـ CheckBox عبر Interface | `Fragment3.OnFragment3Listener` + `MainActivity.onAgreementChanged(...)` |
| تغيير حالة ونص الزر ديناميكياً | `Fragment3.updateFinishButton(...)` |

## تسلسل البيانات (Data Flow)

```
Fragment2
   │  (1) يضغط المستخدم Continue
   ▼
OnFragment2Listener.onFragment2Continue(name)
   │
   ▼
MainActivity
   │  (2) ينشئ Fragment3.newInstance(name)
   ▼
Fragment3  ← يعرض الاسم
   │  (3) يتغير الـ CheckBox
   ▼
OnFragment3Listener.onAgreementChanged(isChecked)
   │
   ▼
MainActivity
   │  (4) يستدعي updateFinishButton(isChecked)
   ▼
Fragment3  ← الزر يتفعل/يتعطل + النص يتغير
```

## الملفات المعدّلة / المضافة

- `app/src/main/java/com/example/myapplication/MainActivity.java` (معدّل)
- `app/src/main/java/com/example/myapplication/Fragment1.java` (معدّل)
- `app/src/main/java/com/example/myapplication/Fragment2.java` (معدّل)
- `app/src/main/java/com/example/myapplication/Fragment3.java` (**جديد**)
- `app/src/main/res/layout/activity_main.xml` (معدّل)
- `app/src/main/res/layout/fragment_1.xml` (معدّل)
- `app/src/main/res/layout/fragment_2.xml` (معدّل)
- `app/src/main/res/layout/fragment_3.xml` (**جديد**)
- `app/src/main/res/values/strings.xml` (معدّل)

## المتطلبات التقنية

| الإعداد | القيمة |
|---|---|
| `compileSdk` | **36** |
| `targetSdk` | 33 |
| `minSdk` | 24 |
| Android Gradle Plugin (AGP) | **8.13.0** |
| Gradle | **8.13** |
| JDK | 17 أو أحدث |
| المكتبات | AppCompat 1.7.1، Material 1.13.0، ConstraintLayout 2.2.1 |

> تم رفع `compileSdk` إلى 36 ليتوافق مع الـ SDK المثبّت عندك، مع إبقاء `targetSdk` على 33 حتى لا يتأثر شكل التطبيق بتغييرات Edge-to-Edge في Android 15/16.

## التشغيل على Android Studio

1. افتح **Android Studio**.
2. اختر `File > Open` وحدّد مجلد المشروع:
   `C:\Users\LENOVO LOQ\Documents\Assignment1`

   > مهم: خلّي مسار المشروع بأحرف إنجليزية. المسارات العربية تكسر سكربت `gradlew.bat` على ويندوز.
3. انتظر انتهاء **Gradle Sync**. أول مرة قد يحتاج ينزّل Gradle 8.13 وAGP 8.13.0.
4. إذا طلب منك الـ IDE تحديد **Gradle JDK**، اختر **JDK 17**:
   `File > Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK`
5. شغّل التطبيق على Emulator أو هاتف حقيقي.

> إذا صار خطأ شبكة أثناء تحميل AGP من Google Maven، استخدم VPN/Proxy أو أضف مرآة Maven مؤقتاً من إعدادات Gradle.


## سكربت فيديو التوضيح (3 - 5 دقائق)

1. افتح المشروع في Android Studio واعرض الملفات بسرعة.
2. شغّل التطبيق واعرض شاشة الترحيب.
3. اضغط `Get Started` واملأ بيانات Fragment2 (اسم، إيميل، جنس، عمر، جامعة).
4. اشرح أن الضغط على `Continue` يرسل الاسم عبر Interface إلى `MainActivity`.
5. أثبت أن الاسم ظهر في Fragment3.
6. جرّب الـ CheckBox:
   - قبل التحديد: الزر معطل ونصه `Continue`.
   - بعد التحديد: الزر يتفعل ونصه `Finish`.
   - بعد إلغاء التحديد: يرجع معطلاً ونصه `Continue`.
7. اشرح الكود بسرعة: الـ interfaces، الـ Bundle، و`setOnCheckedChangeListener`.

## ملاحظات للتسليم

- ارفع المشروع على GitHub Repository **عام (Public)**.
- لا ترفع مجلدات البناء مثل `build/` و `.gradle/` و `.idea/`.
- ارفع فيديو الشرح على Google Drive واجعله متاحاً لأي شخص يملك الرابط.
